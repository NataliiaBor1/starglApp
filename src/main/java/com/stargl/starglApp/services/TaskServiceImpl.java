package com.stargl.starglApp.services;

import com.stargl.starglApp.dtos.TaskDto;
import com.stargl.starglApp.dtos.UserDto;
import com.stargl.starglApp.entities.Task;
import com.stargl.starglApp.entities.User;
import com.stargl.starglApp.enums.Statuses;
import com.stargl.starglApp.repositories.TaskRepository;
import com.stargl.starglApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional
    public List<String> addTask(TaskDto taskDto, Long parentId, Long childId) {
        List<String> response = new ArrayList<>();

        Optional<User> parentOptional = userRepository.findById(parentId);
        Optional<User> childOptional = userRepository.findById(childId);

        if (parentOptional.isPresent() && childOptional.isPresent()) {
            Task task = new Task(taskDto);
            parentOptional.ifPresent(task::setAssigner);
            childOptional.ifPresent(task::setAssignee);
            taskRepository.saveAndFlush(task);
            response.add("Task was successfully created");
            return response;
        } else {
            response.add("User is not found. Creation of task is not possible");
        }
        return response;
    }

    @Override
    @Transactional
    public void deleteTaskById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
//        taskOptional.ifPresent(task) {
//            taskRepository.delete(task);
//         };
        taskOptional.ifPresent(task -> taskRepository.delete(task));
    }

    @Override
    @Transactional
    public void updateTaskById(TaskDto taskDto) {
        Optional<Task> taskOptional = taskRepository.findById(taskDto.getId());
        taskOptional.ifPresent(task -> {
            task.setBody(taskDto.getBody());
            taskRepository.saveAndFlush(task);
        });
    }

    @Override
    public Optional<TaskDto> getTaskById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            return Optional.of(new TaskDto(taskOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<TaskDto> getAllTasksByAssignerId(Long assignerId) {
        Optional<User> userOptional = userRepository.findById(assignerId);
        if (userOptional.isPresent()) {
            List<Task> taskList = taskRepository.findAllByAssignerEquals(userOptional.get());
            return taskList.stream().map(task -> new TaskDto(task)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<TaskDto> getAllTasksByAssigneeId(Long assigneeId) {
        Optional<User> userOptional = userRepository.findById(assigneeId);
        if (userOptional.isPresent()) {
            List<Task> taskList = taskRepository.findAllByAssigneeEquals(userOptional.get());
            return taskList.stream().map(task -> new TaskDto(task)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public List<String> completeTaskByTaskId(Long taskId, int star) {
        List<String> response = new ArrayList<>();
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            if (taskOptional.get().getStatus().equals(Statuses.DONE)) {
                response.add("Your task was completed earlier");
                return response;
            }

            taskOptional.ifPresent(task -> {
                task.setStatus(Statuses.DONE);
                taskRepository.save(task);
            });
            User child = taskOptional.get().getAssignee();
            child.setStarNum(child.getStarNum() + star);
            userRepository.save(child);
            response.add("Your task is completed successfully");
        } else {
            response.add("This task is not found. Try another task id");
        }
        return response;
    }

}

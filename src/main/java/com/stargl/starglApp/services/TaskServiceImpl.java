package com.stargl.starglApp.services;

import com.stargl.starglApp.dtos.TaskDto;
import com.stargl.starglApp.entities.Task;
import com.stargl.starglApp.entities.User;
import com.stargl.starglApp.repositories.TaskRepository;
import com.stargl.starglApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<String> addTask(TaskDto taskDto, Long userId) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            Task task = new Task(taskDto);
            userOptional.ifPresent(task::setAssigner);
            taskRepository.saveAndFlush(task);
            response.add("Task was successfully created");
            return response;
        }
        else {
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
     public List<TaskDto> getAllTasksByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Task> taskList = taskRepository.findAllById(userOptional.get());
            return taskList.stream().map(task -> new TaskDto(task)).collect(Collectors.toList());
        }
        return Collections.emptyList();
     }







}

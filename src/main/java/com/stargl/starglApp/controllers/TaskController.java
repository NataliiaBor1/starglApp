package com.stargl.starglApp.controllers;

import com.stargl.starglApp.dtos.TaskDto;
import com.stargl.starglApp.enums.Statuses;
import com.stargl.starglApp.repositories.UserRepository;
import com.stargl.starglApp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/starglApp/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String toHome() {
        return "index";
    }

    @GetMapping("/assigner/{assignerId}")
    public List<TaskDto> getTasksByAssigner(@PathVariable Long assignerId) {
        return taskService.getAllTasksByAssignerId(assignerId);
    }

    @GetMapping("/assignee/{assigneeId}")
    public List<TaskDto> getTasksByAssignee(@PathVariable Long assigneeId) {
        return taskService.getAllTasksByAssigneeId(assigneeId);
    }

    @GetMapping("/{taskId}")
    public Optional<TaskDto> getTaskById(@PathVariable Long taskId) {  // work in Postman
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/user/{parentId}/{childId}")
    public void addTask(@RequestBody TaskDto taskDto, @PathVariable Long parentId, @PathVariable Long childId) {
        taskDto.setStatus(Statuses.STARTED);
        taskService.addTask(taskDto, parentId, childId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTaskById(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
    }

    @PatchMapping("/{taskId}")
    public List<String> completeTaskByTaskId(@PathVariable Long taskId, @RequestParam int star) {
        return taskService.completeTaskByTaskId(taskId, star);
    }

    @PutMapping
    public void updateTask(@RequestBody TaskDto taskDto) {
        taskService.updateTaskById(taskDto);
    }


}

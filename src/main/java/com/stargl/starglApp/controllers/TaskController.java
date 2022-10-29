package com.stargl.starglApp.controllers;

import com.stargl.starglApp.dtos.TaskDto;
import com.stargl.starglApp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/user/{userId}")
    public List<TaskDto> getTasksByUser(@PathVariable Long userId) {
        return taskService.getAllTasksByUserId(userId);
    }

    @GetMapping("/{taskId}")
    public Optional<TaskDto> getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/user/{userId}")
    public void addTask(@RequestBody TaskDto taskDto, @PathVariable Long userId) {
        taskService.addTask(taskDto, userId);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTaskById(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
    }

    @PutMapping
    public void updateTask(@RequestBody TaskDto taskDto) {
        taskService.updateTaskById(taskDto);
    }


}

package com.stargl.starglApp.controllers;

import com.stargl.starglApp.dtos.TaskDto;
import com.stargl.starglApp.dtos.UserDto;
import com.stargl.starglApp.entities.User;
import com.stargl.starglApp.repositories.UserRepository;
import com.stargl.starglApp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/user/{userId}")                           // 500 server error
    public List<TaskDto> getTasksByUser(@PathVariable Long userId) {
        return taskService.getAllTasksByUserId(userId);
    }

    @GetMapping("/{taskId}")
    public Optional<TaskDto> getTaskById(@PathVariable Long taskId) {  // work in Postman
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/user/{parentId}/{childId}")              // does not work in Postman   title is not setting up
    public void addTask(@RequestBody TaskDto taskDto, @PathVariable Long parentId, @PathVariable Long childId) {
        taskService.addTask(taskDto, parentId, childId);
    }

    @DeleteMapping("/{taskId}")                                 // work in Postman
    public void deleteTaskById(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
    }

    @PutMapping                                 // work in Postman but title is not updated
    public void updateTask(@RequestBody TaskDto taskDto) {
        taskService.updateTaskById(taskDto);
    }


}

package com.stargl.starglApp.controllers;

import com.stargl.starglApp.entities.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class IndexController {

    @RequestMapping("/index")
    public List<Task> retrieveAllTasks () {
        return Arrays.asList(
        );
    }
}

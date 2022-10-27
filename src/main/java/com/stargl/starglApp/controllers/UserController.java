package com.stargl.starglApp.controllers;

import com.stargl.starglApp.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @PostMapping("/api/users")
    public void saveUser(@RequestBody User user) {

    }
}

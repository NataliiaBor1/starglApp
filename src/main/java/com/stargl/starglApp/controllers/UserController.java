package com.stargl.starglApp.controllers;

import com.stargl.starglApp.dtos.UserDto;
import com.stargl.starglApp.entities.Task;
import com.stargl.starglApp.entities.User;
import com.stargl.starglApp.repositories.TaskRepository;
import com.stargl.starglApp.repositories.UserRepository;
import com.stargl.starglApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private TaskRepository taskRepository;


    @PostMapping("/register")
    public List<String> registerUser(@RequestBody UserDto userDto) {  // finally! work in Postman
        String encodedPassw = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassw);
        return userService.registerUser(userDto);
    }

    @GetMapping
    public Optional<UserDto> getUserById(@RequestParam Long id) {  // status 200 OK. Data or null // checked in Postman
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            response.add("User is found successfully");
            return Optional.of(new UserDto(userOptional.get()));
        }
        response.add("User is not found");
        return Optional.empty();
    }

    @PostMapping("/login")  // finally working, checked in Postman
    public List<String> userLogin(@RequestBody UserDto userDto) {
        return userService.userLogin(userDto);
    }





}

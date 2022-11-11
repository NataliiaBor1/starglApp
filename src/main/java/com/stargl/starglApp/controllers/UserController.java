package com.stargl.starglApp.controllers;

import com.stargl.starglApp.dtos.UserDto;
import com.stargl.starglApp.entities.Task;
import com.stargl.starglApp.entities.User;
import com.stargl.starglApp.enums.Roles;
import com.stargl.starglApp.repositories.TaskRepository;
import com.stargl.starglApp.repositories.UserRepository;
import com.stargl.starglApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/starglApp/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public List<String> registerParent(@RequestBody UserDto userDto) {  // finally! work in Postman
        String encodedPassw = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassw);
        userDto.setRole(Roles.PARENT);
        return userService.registerUser(userDto);
    }

    @PostMapping("/addChild/{parentId}")
    public List<String> registerChild(@RequestBody UserDto userDto, @PathVariable Long parentId) {  //??????????????
        String encodedPassw = passwordEncoder.encode(userDto.getPassword());

        userDto.setPassword(encodedPassw);
        userDto.setRole(Roles.CHILD);
        userDto.setParentId(parentId);
        return userService.registerUser(userDto);
    }

    @PostMapping("/login")  //  work  in Postman
    public List<String> userLogin(@RequestBody UserDto userDto) {  // account-role saved for cookie
        List<String> response = new ArrayList<>();
        // save username and role like cookies ???
        return userService.userLogin(userDto);
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

    @GetMapping("/{parentId}")
    public List<UserDto> getAllChildrenByUserId(@PathVariable Long parentId) { // work in postman
        return userService.getAllChildrenByUserId(parentId);
    }

}

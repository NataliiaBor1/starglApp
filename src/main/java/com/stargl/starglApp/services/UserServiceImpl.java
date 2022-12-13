package com.stargl.starglApp.services;

import com.stargl.starglApp.dtos.UserDto;
import com.stargl.starglApp.entities.User;
import com.stargl.starglApp.enums.Roles;
import com.stargl.starglApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public List<String> registerUser(UserDto userDto) {
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        try {
            userRepository.saveAndFlush(user);
            if (user.getRole().equals(Roles.PARENT)) {
                response.add("http://localhost:8080/login.html");
            }
            else {
                response.add("You have registered your child successfully");
                Optional<User> parentOptional = userRepository.findById(userDto.getParentId());
//                int chilAmount = parent.get().getChildrenAmount();
//                chilAmount += 1;
//                parent.get().setChildrenAmount(chilAmount);
//                parent.ifPresent(user::setParent);
                user.setParent(parentOptional.get());
                userRepository.save(user);
            }
        }
        catch (Exception e) {
            response.add("This username already exists. Try new one.");
        }
        return response;
    }


    @Override
    public List<String> userLogin(UserDto userDto) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())) {
                if (userOptional.get().getRole().equals(Roles.PARENT)) {
                    response.add("http://localhost:8080/parent.html");
                }
                else {
                    response.add("http://localhost:8080/child.html");
                }
                response.add(String.valueOf(userOptional.get().getId()));
            }
            else {
                response.add("Username or password is incorrect");
            }
        }
        else {
            response.add("Username or password is incorrect");
        }
        return response;
    }

    @Override
    public List<UserDto> getAllChildrenByUserId(Long parentId) {
        Optional<User> userOptional = userRepository.findById(parentId);
        if (userOptional.isPresent()) {
            List<User> childrenList = userRepository.findAllByParentEquals(userOptional.get());
            return childrenList.stream().map(child -> new UserDto(child)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}

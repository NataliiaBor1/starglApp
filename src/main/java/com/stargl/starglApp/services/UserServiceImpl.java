package com.stargl.starglApp.services;

import com.stargl.starglApp.dtos.UserDto;
import com.stargl.starglApp.entities.User;
import com.stargl.starglApp.enums.Roles;
import com.stargl.starglApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                Optional<User> parent = userRepository.findById(user.getParentId());
                int chilAmount = parent.get().getChildrenAmount();
                chilAmount += 1;
                parent.get().setChildrenAmount(chilAmount);
                userRepository.save(parent.get());
            }
        }
        catch (Exception e) {
            response.add("This username already exists. Try new one.");
        }
        return response;
    }

    @Transactional
    public List<String> registerChild(UserDto userDto) {  // ?????????????
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        userRepository.saveAndFlush(user);
        response.add("http://localhost:8080/parent.html");
        return response;
    }

    @Override
    public List<String> userLogin(UserDto userDto) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())) {
                if (userOptional.get().getRole().equals(Roles.PARENT)) {  // save username and role
                    response.add("http://localhost:8080/parent.html");
                }
                else {
                    response.add("http://localhost:8080/child.html");
                }
                response.add(String.valueOf(userOptional.get().getId()));

//                Cookie cookie = new Cookie("data", "Come_to_the_dark_side");//создаем объект Cookie,
//                //в конструкторе указываем значения для name и value
//                cookie.setPath("/");//устанавливаем путь
//                cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
//                response.add(cookie);   addCookie(cookie);//добавляем Cookie в запрос
//                response.setContentType("text/plain");//устанавливаем контекст
//                return ResponseEntity.ok().body(HttpStatus.OK);
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
}

//package com.stargl.starglApp.controllers;

//import com.stargl.starglApp.entities.Task;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Controller
//public class IndexController {
//
//    @RequestMapping("/login")
//    public String retrieveAllTasks () {
//        return "login";
//    }
//
//    @RequestMapping("/")
//    public String toHome(Model model) {
//        model.addAttribute("username", "Nataliia");
//        return "index";
//    }
//
//    @GetMapping("/about")
//    public String about(Model model) {
//        model.addAttribute("title", "Page about application");
//        return "about";
//    }
//
//    @GetMapping("/register")
//    public String register(Model model) {
//        model.addAttribute("title", "Registration Page");
//        return "register";
//    }
//
//}

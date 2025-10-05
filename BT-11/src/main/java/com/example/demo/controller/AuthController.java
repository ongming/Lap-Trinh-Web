package com.example.demo.controller;

import com.example.demo.config.JwtService;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userService.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User found = userService.findByUsername(user.getUsername());
        if (found != null && found.getPassword().equals(user.getPassword())) {
            return jwtService.generateToken(user.getUsername());
        }
        return "Invalid credentials";
    }
}

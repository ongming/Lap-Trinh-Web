package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User findByUsername(String username);
    void save(User user);
}

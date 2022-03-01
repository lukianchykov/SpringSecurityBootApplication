package com.example.springsecuritybootapplication.services.interfaces;

import com.example.springsecuritybootapplication.entities.User;

import java.util.List;

public interface IUserService {
    void createUser(User user);
    User get(Long id);
    boolean userExists(String username);
    List<User> getList();
}

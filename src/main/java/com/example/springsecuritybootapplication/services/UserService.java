package com.example.springsecuritybootapplication.services;

import com.example.springsecuritybootapplication.entities.Role;
import com.example.springsecuritybootapplication.entities.User;
import com.example.springsecuritybootapplication.repositories.UserRepository;
import com.example.springsecuritybootapplication.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    @Transactional
    public void createUser(User user) {

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>(Arrays.asList(Role.USER)));
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    @PostAuthorize("returnObject.username == authentication.name")
    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    @PostFilter("filterObject.roles.size() > 1")
    public List<User> getList() {
        return userRepository.findAll();
    }

}

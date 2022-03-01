package com.example.springsecuritybootapplication.config;

import com.example.springsecuritybootapplication.entities.Role;
import com.example.springsecuritybootapplication.entities.User;
import com.example.springsecuritybootapplication.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DefaultDataConfig {

  private final IUserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public DefaultDataConfig(IUserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  private void createDefaultUsers() {
    List.of(
            User.builder().username("john").password(passwordEncoder.encode("123")).build(),
            User.builder().username("jane").password(passwordEncoder.encode("123")).build()
    ).forEach(userService::createUser);

    userService.createUser(
            User.builder().username("admin").password(passwordEncoder.encode("admin")).roles(new HashSet<>(Arrays.asList(Role.ADMIN))).build()
    );

    userService.createUser(
            User.builder().username("moder").password(passwordEncoder.encode("moder")).roles(new HashSet<>(Arrays.asList(Role.MODERATOR))).build()
    );

    userService.createUser(
            User.builder().username("umoderator").password(passwordEncoder.encode("12345")).roles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.MODERATOR))).build()
    );
  }

}

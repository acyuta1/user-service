package com.acyuta.microservices.userservice.domain.controller;

import com.acyuta.microservices.userservice.domain.model.User;
import com.acyuta.microservices.userservice.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers(){
        log.info("fetching all users");
        return userService.retrieveUsers();
    }

    @PostMapping("/users/add")
    public String addUser(@RequestBody List<User> users){
        log.info("adding new users - {}", users);
        return userService.addUsers(users) + " users added successfully.";
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") User user){
        log.info("fetched user {}", user);
        return user;
    }

    @GetMapping("/users/by-email")
    public User getUserByEmail(@RequestParam("email") String email){
        log.info("trying to fetch user with email -- {}", email);
        return userService.fetchByEmail(email);
    }
}

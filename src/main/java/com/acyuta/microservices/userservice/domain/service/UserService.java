package com.acyuta.microservices.userservice.domain.service;

import com.acyuta.microservices.userservice.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> retrieveUsers();

    Integer addUsers(List<User> users);

    User fetchByEmail(String email);
}

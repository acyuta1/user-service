package com.acyuta.microservices.userservice.domain.service;

import com.acyuta.microservices.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private Environment environment;

    private void checkIfAlreadyExists(String email) {
        if (userRepository.existsByEmail(email))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already exists with provided email");
    }

    @Override
    public List<User> retrieveUsers() {
        return userRepository.findAll();
    }

    @Override
    public Integer addUsers(List<User> users) {
        AtomicInteger usersAdded = new AtomicInteger(0);
        users
                .stream()
                .map(user -> {
                    if (userRepository.existsByEmail(user.getEmail()))
                        return null;
                    return user;
                }).filter(Objects::nonNull)
                .forEach(user -> {
                    usersAdded.incrementAndGet();
                    userRepository.save(user);
                });
        return usersAdded.get();
    }

    @Override
    public User fetchByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user with email passed")).setPort(Integer.parseInt(environment.getProperty("local.server.port")));
    }
}

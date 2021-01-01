package com.acyuta.microservices.userservice.domain.converter;

import com.acyuta.microservices.userservice.domain.model.User;
import com.acyuta.microservices.userservice.domain.service.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserConverter implements Converter<String, User> {

    private final UserRepository userRepository;

    @Autowired
    private Environment environment;
    /**
     * Fetches a user when the id is provided.
     *
     * @param id String
     * @return user.
     */
    @Override
    public User convert(String id) {
        var user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND"));
        user.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        return user;
    }
}

package com.taskflow.api.service;

import org.springframework.stereotype.Service;

import com.taskflow.api.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

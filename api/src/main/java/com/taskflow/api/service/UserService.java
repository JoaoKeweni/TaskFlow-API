package com.taskflow.api.service;

import org.springframework.stereotype.Service;

import com.taskflow.api.domain.entity.User;
import com.taskflow.api.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // TODO regra pra conferir email existente
        // TODO regra pra conferir username existente
        return userRepository.save(user);
    }

}

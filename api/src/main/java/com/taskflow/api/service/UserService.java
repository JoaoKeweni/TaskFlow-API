package com.taskflow.api.service;

import org.springframework.stereotype.Service;

import com.taskflow.api.domain.dto.UserRequestDTO;
import com.taskflow.api.domain.entity.User;
import com.taskflow.api.repository.UserRepository;
import com.taskflow.api.domain.dto.UserResponseDTO;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        // TODO: regra pra conferir email existente(OK)
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Este email já está cadatrado");
        }
        // TODO: regra pra conferir username existente(OK)
        if (userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("Este username já está em uso");
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail());
    }

}

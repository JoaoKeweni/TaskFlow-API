package com.taskflow.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.api.domain.dto.UserRequestDTO;
import com.taskflow.api.domain.dto.UserResponseDTO;
import com.taskflow.api.domain.entity.User;
import com.taskflow.api.service.UserService;

//@RestController define que a classe é um controlador REST
@RestController
// @RequestMapping define a URL base para todos os endpoints
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO dto) {
        // @RequestBody avisa o Spring para pegar o JSON que veio na requisição
        // e converter magicamente para o nosso objeto User.
        // Passamos o pedido para o nosso "cozinheiro" (UserService)
        UserResponseDTO savedUser = userService.createUser(dto);
        // Devolvemos Status 201 (Created) junto com os dados do usuário recém salvo!
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

}

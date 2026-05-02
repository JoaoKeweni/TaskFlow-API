package com.taskflow.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardRequestDTO(
        @NotBlank(message = "O nome do quadro é obrigatório")
        String name,
        
        String description,
        
        @NotNull(message = "O ID do usuário é obrigatório")
        Long userId
) {
}

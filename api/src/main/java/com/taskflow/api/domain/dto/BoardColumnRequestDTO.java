package com.taskflow.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardColumnRequestDTO(
        @NotBlank(message = "O nome da coluna é obrigatório")
        String name,
        
        @NotNull(message = "A posição é obrigatória")
        Integer position,
        
        @NotNull(message = "O ID do quadro é obrigatório")
        Long boardId
) {
}

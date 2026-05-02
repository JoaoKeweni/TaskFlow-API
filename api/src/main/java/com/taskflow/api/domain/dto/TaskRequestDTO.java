package com.taskflow.api.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRequestDTO(
        @NotBlank(message = "O título da tarefa é obrigatório")
        String title,
        
        String description,
        
        @NotBlank(message = "A prioridade é obrigatória")
        String priority,
        
        @NotNull(message = "O ID da coluna é obrigatório")
        Long columnId,
        
        @NotNull(message = "O ID do usuário é obrigatório")
        Long userId) {
}

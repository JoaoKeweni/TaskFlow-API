package com.taskflow.api.domain.dto;

public record BoardColumnResponseDTO(
        Long id,
        String name,
        Integer position,
        String boardName) {

}

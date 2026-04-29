package com.taskflow.api.domain.dto;

public record BoardResponseDTO(
        Long id,
        String name,
        String description,
        String ownerUsername) {

}

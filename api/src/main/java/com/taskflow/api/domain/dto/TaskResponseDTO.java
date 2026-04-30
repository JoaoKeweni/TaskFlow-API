package com.taskflow.api.domain.dto;

public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        String priority,
        String columnName,
        String userName) {

}

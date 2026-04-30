package com.taskflow.api.domain.dto;

public record TaskRequestDTO(
        String title,
        String description,
        String priority,
        Long columnId,
        Long userId) {

}

package com.taskflow.api.domain.dto;

import java.util.List;

public record BoardColumnResponseDTO(
        Long id,
        String name,
        Integer position,
        String boardName,
        List<TaskResponseDTO> tasks) {

}

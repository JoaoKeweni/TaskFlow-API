package com.taskflow.api.domain.dto;

import java.util.List;

public record BoardResponseDTO(
        Long id,
        String name,
        String description,
        String ownerUsername,
        List<BoardColumnResponseDTO> columns) {

}

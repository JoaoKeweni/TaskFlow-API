package com.taskflow.api.domain.dto;

public record BoardColumnRequestDTO(
                String name,
                Integer position,
                Long boardId) {

}

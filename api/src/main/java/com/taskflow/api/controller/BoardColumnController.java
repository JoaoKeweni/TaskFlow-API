package com.taskflow.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.taskflow.api.domain.dto.BoardColumnRequestDTO;
import com.taskflow.api.domain.dto.BoardColumnResponseDTO;
import com.taskflow.api.service.BoardColumnService;

@RestController
@RequestMapping("/api/columns")
public class BoardColumnController {

    private final BoardColumnService boardColumnService;

    public BoardColumnController(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }

    @PostMapping
    public ResponseEntity<BoardColumnResponseDTO> createColumn(@Valid @RequestBody BoardColumnRequestDTO dto) {
        BoardColumnResponseDTO savedColumn = boardColumnService.createBoardColumn(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedColumn);
    }
}

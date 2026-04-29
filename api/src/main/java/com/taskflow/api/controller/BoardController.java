package com.taskflow.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.api.domain.dto.BoardRequestDTO;
import com.taskflow.api.domain.dto.BoardResponseDTO;
import com.taskflow.api.domain.entity.Board;
import com.taskflow.api.service.BoardService;

@RestController
@RequestMapping("api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<BoardResponseDTO> createBoard(@RequestBody BoardRequestDTO dto) {

        BoardResponseDTO savedBoard = this.boardService.createBoard(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBoard);
    }

}

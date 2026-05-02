package com.taskflow.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.taskflow.api.domain.dto.BoardRequestDTO;
import com.taskflow.api.domain.dto.BoardResponseDTO;
import com.taskflow.api.service.BoardService;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    // Injeção de dependência do Service
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // Rota para Criar um novo Quadro
    @PostMapping
    public ResponseEntity<BoardResponseDTO> createBoard(@Valid @RequestBody BoardRequestDTO dto) {
        // Passamos o DTO para o nosso Service processar e salvar
        BoardResponseDTO savedBoard = boardService.createBoard(dto);

        // Devolvemos Status 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBoard);
    }

    // Rota para Listar todos os Quadros
    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAllBoards() {
        // Pedimos para o Service nos dar todos os quadros
        List<BoardResponseDTO> boards = boardService.findAll();

        // Devolvemos Status 200 (OK). O Spring tem esse atalho ".ok()" bem prático!
        return ResponseEntity.ok(boards);
    }
}

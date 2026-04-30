package com.taskflow.api.service;

import java.util.Optional;

import java.util.List;
import org.springframework.stereotype.Service;

import com.taskflow.api.domain.dto.BoardRequestDTO;
import com.taskflow.api.domain.dto.BoardResponseDTO;
import com.taskflow.api.domain.entity.Board;
import com.taskflow.api.domain.entity.User;
import com.taskflow.api.repository.BoardRepository;
import com.taskflow.api.repository.UserRepository;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public BoardResponseDTO createBoard(BoardRequestDTO dto) {
        User owner = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // criar a entidade Board
        Board board = new Board();
        board.setName(dto.name());
        board.setDescription(dto.description());
        board.setUser(owner);

        // salvar no banco
        Board savedBoard = boardRepository.save(board);

        // retornar o DTO como resposta
        return new BoardResponseDTO(
                savedBoard.getId(),
                savedBoard.getName(),
                savedBoard.getDescription(),
                savedBoard.getUser().getUsername());
    }

    public List<BoardResponseDTO> findAll() {
        // 1. Busca todo mundo do banco
        List<Board> boards = boardRepository.findAll();

        // 2. Transforma cada "Board" em um "BoardResponseDTO"
        return boards.stream()
                .map(board -> new BoardResponseDTO(
                        board.getId(),
                        board.getName(),
                        board.getDescription(),
                        board.getUser().getUsername() // Pegamos o nome do dono do Board
                ))
                .toList();
    }

}

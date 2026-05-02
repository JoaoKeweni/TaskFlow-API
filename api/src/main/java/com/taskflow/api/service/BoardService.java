package com.taskflow.api.service;

import java.util.Optional;

import java.util.List;
import org.springframework.stereotype.Service;

import com.taskflow.api.domain.dto.BoardRequestDTO;
import com.taskflow.api.domain.dto.BoardResponseDTO;
import com.taskflow.api.domain.dto.BoardColumnResponseDTO;
import com.taskflow.api.domain.dto.TaskResponseDTO;
import com.taskflow.api.domain.entity.Board;
import com.taskflow.api.domain.entity.User;
import com.taskflow.api.repository.BoardRepository;
import com.taskflow.api.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

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
                savedBoard.getUser().getUsername(),
                new ArrayList<>());
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDTO> findAll() {
        // 1. Busca todo mundo do banco
        List<Board> boards = boardRepository.findAll();

        // 2. Transforma cada "Board" em um "BoardResponseDTO" com colunas e tarefas
        return boards.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private BoardResponseDTO convertToDTO(Board board) {
        List<BoardColumnResponseDTO> columnsDTO = new ArrayList<>();

        if (board.getColumns() != null) {
            columnsDTO = board.getColumns().stream().map(col -> {
                List<TaskResponseDTO> tasksDTO = new ArrayList<>();
                if (col.getTasks() != null) {
                    tasksDTO = col.getTasks().stream().map(task -> new TaskResponseDTO(
                            task.getId(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getPriority(),
                            col.getName(),
                            task.getUser().getUsername()
                    )).toList();
                }

                return new BoardColumnResponseDTO(
                        col.getId(),
                        col.getName(),
                        col.getPosition(),
                        board.getName(),
                        tasksDTO
                );
            }).toList();
        }

        return new BoardResponseDTO(
                board.getId(),
                board.getName(),
                board.getDescription(),
                board.getUser().getUsername(),
                columnsDTO
        );
    }

}

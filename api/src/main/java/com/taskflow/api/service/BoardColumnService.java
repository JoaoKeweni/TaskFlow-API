package com.taskflow.api.service;

import org.springframework.stereotype.Service;

import com.taskflow.api.domain.dto.BoardColumnResponseDTO;
import com.taskflow.api.domain.entity.Board;
import com.taskflow.api.domain.entity.BoardColumn;
import com.taskflow.api.domain.dto.BoardColumnRequestDTO;
import com.taskflow.api.repository.BoardColumnRepository;
import com.taskflow.api.repository.BoardRepository;

@Service
public class BoardColumnService {
    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;

    public BoardColumnService(BoardColumnRepository boardColumnRepository, BoardRepository boardRepository) {
        this.boardColumnRepository = boardColumnRepository;
        this.boardRepository = boardRepository;
    }

    public BoardColumnResponseDTO createBoardColumn(BoardColumnRequestDTO dto) {
        // Passo 1: Busca o Quadro (Board) a qual essa coluna vai pertencer.
        // Lembra que no DTO nós recebemos o boardId? Usamos ele aqui!
        Board board = boardRepository.findById(dto.boardId())
                .orElseThrow(() -> new IllegalArgumentException("Quadro não encontrado"));
        // Passo 2: Cria a nova entidade coluna e preenche com os dados do DTO
        BoardColumn column = new BoardColumn();
        column.setName(dto.name());
        column.setPosition(dto.position());
        column.setBoard(board); // Aqui é a mágica: amarramos a coluna ao quadro que achamos no Passo 1!
        // Passo 3: Salva no banco de dados usando o Repository
        BoardColumn savedColumn = boardColumnRepository.save(column);
        // Passo 4: Converte a entidade salva de volta para o DTO de Resposta
        return new BoardColumnResponseDTO(
                savedColumn.getId(),
                savedColumn.getName(),
                savedColumn.getPosition(),
                savedColumn.getBoard().getName() // Olha que legal: pegamos o nome do Board diretamente da relação!
        );
    }

}

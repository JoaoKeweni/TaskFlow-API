package com.taskflow.api.service;

import org.springframework.stereotype.Service;

import com.taskflow.api.domain.dto.TaskRequestDTO;
import com.taskflow.api.domain.dto.TaskResponseDTO;
import com.taskflow.api.domain.entity.BoardColumn;
import com.taskflow.api.domain.entity.Task;
import com.taskflow.api.domain.entity.User;
import com.taskflow.api.repository.BoardColumnRepository;
import com.taskflow.api.repository.TaskRepository;
import com.taskflow.api.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, BoardColumnRepository boardColumnRepository,
            UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.boardColumnRepository = boardColumnRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        // Passo 1: Busca a Coluna usando o columnId
        // Você vai precisar importar a classe BoardColumn!
        BoardColumn column = boardColumnRepository.findById(dto.columnId())
                .orElseThrow(() -> new IllegalArgumentException("Coluna não encontrada"));

        // Passo 2: Busca o Usuário dono da tarefa usando o userId
        // Você vai precisar importar a classe User!
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Passo 3: Cria a nova Tarefa e preenche com os textos do DTO
        // Você vai precisar importar a classe Task!
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(dto.priority()); // Lembra de mudar para String lá nos DTOs!

        // Passo 4: Amarra a Tarefa nas duas Entidades que achamos!
        task.setBoardColumn(column);
        task.setUser(user);

        // Passo 5: Salva no banco de dados!
        Task savedTask = taskRepository.save(task);

        // Passo 6: Converte para a "Maleta" de Resposta com os nomes bonitos
        return new TaskResponseDTO(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getPriority(),
                savedTask.getBoardColumn().getName(), // Nome da coluna
                savedTask.getUser().getUsername() // Nome do usuário
        );
    }

}

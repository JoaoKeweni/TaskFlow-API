package com.taskflow.api.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return convertToResponseDTO(savedTask);
    }

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        return convertToResponseDTO(task);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        BoardColumn column = boardColumnRepository.findById(dto.columnId())
                .orElseThrow(() -> new IllegalArgumentException("Coluna não encontrada"));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setPriority(dto.priority());
        task.setBoardColumn(column);
        task.setUser(user);

        Task updatedTask = taskRepository.save(task);
        return convertToResponseDTO(updatedTask);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Tarefa não encontrada");
        }
        taskRepository.deleteById(id);
    }

    private TaskResponseDTO convertToResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getBoardColumn().getName(),
                task.getUser().getUsername()
        );
    }

}

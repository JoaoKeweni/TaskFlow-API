package com.taskflow.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taskflow.api.domain.dto.TaskRequestDTO;
import com.taskflow.api.domain.dto.TaskResponseDTO;
import com.taskflow.api.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO dto) {
        TaskResponseDTO savedTask = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }
}

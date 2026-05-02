package com.taskflow.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

// 1. Diz para o Spring: "Ei, fique de olho nos erros de todos os Controllers!"
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 2. Diz para o Spring: "Quando alguém jogar uma IllegalArgumentException,
    // dispare esse método!"
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
            HttpServletRequest request) {

        // Montamos o nosso DTO de Erro com os dados bem organizados
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), // Momento que o erro aconteceu
                HttpStatus.BAD_REQUEST.value(), // Número 400
                ex.getMessage(), // O texto "Este email já está cadastrado", que enviamos do Service!
                request.getRequestURI() // Qual Rota estourou o erro (/api/users)
        );

        // Devolvemos o Status 400 (Bad Request) com o nosso JSON formatado
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Trata os erros de validação (ex: @NotBlank, @NotNull)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        // Junta todas as mensagens de erro de validação em uma única string separada por vírgula
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

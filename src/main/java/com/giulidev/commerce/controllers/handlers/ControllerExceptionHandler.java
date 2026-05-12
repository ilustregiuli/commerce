package com.giulidev.commerce.controllers.handlers;

import com.giulidev.commerce.dto.CustomError;
import com.giulidev.commerce.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

// classe criada com a Annotation "@ControllerAdvice", que permite o tratamento global de todoh projeto
//  sem precisar colocar "try catch" nos locais onde possa estourar
@ControllerAdvice
public class ControllerExceptionHandler {

    // esse metodo foi criado especificamente para tratar das exceptions "ResourceNotFound" que forem
    // lançadas no Service e vão parar no controller
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Metodo que pega a validação dos argumentos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> validaArgument(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String erros = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        CustomError err = new CustomError(Instant.now(), status.value(), erros, request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}

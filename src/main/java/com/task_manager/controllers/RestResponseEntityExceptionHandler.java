package com.task_manager.controllers;

import com.task_manager.exceptions.AuthorNotFound;
import com.task_manager.exceptions.TaskNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthorNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handlerAuthorNotFound(RuntimeException e) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("message: ", e.getMessage());
        response.put("Error: ", HttpStatus.NOT_FOUND.toString());
        response.put("timestamp: ", LocalDateTime.now());

        return response;
    }

    @SuppressWarnings("all")
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> response = new HashMap<>();


        ex.getBindingResult().getAllErrors().forEach(
                (error) -> {

                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();

                    response.put(fieldName, message);
                }
        );

        response.put("Error: ", status.toString());
        response.put("timestamp: ", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFound.class)
    public Map<String, Object> handleTaskNotFound(RuntimeException e) {

        Map<String, Object> response = new LinkedHashMap<>();

        response.put("timestamp", LocalDateTime.now());
        response.put("error", HttpStatus.NOT_FOUND);
        response.put("message", e.getMessage());

        return response;
    }
}

package com.desafiotecnico.product_service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

    @Nullable
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(HttpStatus.BAD_REQUEST, errors);
        logger.info("REQUIRED FIELDS: " + responseDTO.getErrors());
        return handleExceptionInternal(ex, responseDTO, headers, status, request);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(
            ex.getStatus(),
            ex.getMessage()
        );

        return handleExceptionInternal(
            ex,
            responseDTO,
            new HttpHeaders(),
            HttpStatus.valueOf(responseDTO.getStatusCode()),
            request
        );
    }
}
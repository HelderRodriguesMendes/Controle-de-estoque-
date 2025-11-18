package com.desafiotecnico.product_service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

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
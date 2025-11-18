package com.desafiotecnico.product_service.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponseDTO {
    private int statusCode;
    private String status;
    private LocalDateTime timestamp;
    private Object errors;

    public ExceptionResponseDTO(HttpStatus httpStatus, Object errors) {
        this.statusCode = httpStatus.value();
        this.status = httpStatus.name();
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}

package com.desafiotecnico.stock_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Boolean lowStock;
}

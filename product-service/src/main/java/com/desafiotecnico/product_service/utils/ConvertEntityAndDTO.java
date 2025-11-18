package com.desafiotecnico.product_service.utils;

import com.desafiotecnico.product_service.dto.request.ProductRequestDTO;
import com.desafiotecnico.product_service.dto.response.ProductResponseDTO;
import com.desafiotecnico.product_service.entity.Product;

public class ConvertEntityAndDTO {

    private ConvertEntityAndDTO() {
    }

    public static Product convertDtoToEntity(ProductRequestDTO productRequestDTO) {
        return Product.builder()
            .id(productRequestDTO.getId())
            .name(productRequestDTO.getName())
            .description(productRequestDTO.getDescription())
            .price(productRequestDTO.getPrice())
            .quantity(productRequestDTO.getQuantity())
            .build();
    }

    public static ProductResponseDTO convertEntityToDto(Product product) {
        return ProductResponseDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .build();
    }
}

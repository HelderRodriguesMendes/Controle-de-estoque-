package com.desafiotecnico.stock_service.service;

import com.desafiotecnico.stock_service.dto.request.ProductRequestDTO;
import com.desafiotecnico.stock_service.dto.response.ProductResponseDTO;
import com.desafiotecnico.stock_service.feignclient.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {
    private final ProductClient productClient;

    @Override
    public ProductRequestDTO validStock(Long productId) {
        log.info("Validating stock for product ID: {}", productId);
        ProductResponseDTO product = getByProduct(productId);

        boolean lowStock = product.getQuantity() < 10;
        log.info("Product {} low stock: {}", productId, lowStock);

        return ProductRequestDTO.builder()
            .id(productId)
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .lowStock(lowStock)
            .build();
    }

    private ProductResponseDTO getByProduct(Long productId) {
        log.info("Retrieving stock for product ID: {}", productId);
        ResponseEntity<ProductResponseDTO> response = productClient.getProductById(productId);
        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            log.info("Stock found for product ID: {}", productId);
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch product with ID: " + productId);
        }
    }
}

package com.desafiotecnico.stock_service.controller;

import com.desafiotecnico.stock_service.dto.request.ProductRequestDTO;
import com.desafiotecnico.stock_service.service.ProductStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product-stock")
@Tag(name = "Stock", description = "Consulta de estoque")
public class ProductStockController {
    private final ProductStockService productStockService;

    @GetMapping("/{id}")
    @Operation(summary = "Consultar estoque de um produto via FeignClient")
    public ResponseEntity<ProductRequestDTO> validStock(@PathVariable Long id) {
        ProductRequestDTO product = productStockService.validStock(id);
        return ResponseEntity.ok(product);
    }
}

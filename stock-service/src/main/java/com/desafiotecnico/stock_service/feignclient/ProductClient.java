package com.desafiotecnico.stock_service.feignclient;

import com.desafiotecnico.stock_service.dto.response.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${feign.client.config.product-service.url}")
public interface ProductClient {
    @GetMapping("/product/{id}")
    ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id);
}

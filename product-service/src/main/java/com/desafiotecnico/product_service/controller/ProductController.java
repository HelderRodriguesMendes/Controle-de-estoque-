package com.desafiotecnico.product_service.controller;

import com.desafiotecnico.product_service.dto.request.ProductRequestDTO;
import com.desafiotecnico.product_service.dto.response.ProductResponseDTO;
import com.desafiotecnico.product_service.entity.Product;
import com.desafiotecnico.product_service.service.ProductService;
import com.desafiotecnico.product_service.utils.ConvertEntityAndDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        Product productSaved = productService.save(ConvertEntityAndDTO.convertDtoToEntity(productRequestDTO));
        return new ResponseEntity<>(ConvertEntityAndDTO.convertEntityToDto(productSaved), HttpStatus.CREATED);
    }
}

package com.desafiotecnico.product_service.controller;

import com.desafiotecnico.product_service.dto.request.ProductRequestDTO;
import com.desafiotecnico.product_service.dto.response.ProductResponseDTO;
import com.desafiotecnico.product_service.entity.Product;
import com.desafiotecnico.product_service.service.ProductService;
import com.desafiotecnico.product_service.utils.ConvertEntityAndDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Tag(name = "Product", description = "Gerenciamento de Produtos")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Cadastrar novo produto")
    @PostMapping("/save")
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        Product productSaved = productService.save(ConvertEntityAndDTO.convertDtoToEntity(productRequestDTO));
        return new ResponseEntity<>(ConvertEntityAndDTO.convertEntityToDto(productSaved), HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar produtos pelo nome")
    @GetMapping(params = "name")
    public ResponseEntity<List<Product>> findByName(@RequestParam String name) {
        List<Product> products = productService.findByName(name);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Listar todos os produtos")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> listAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(
            products.stream()
                .map(ConvertEntityAndDTO::convertEntityToDto)
                .toList()
        );
    }

    @Operation(summary = "Buscar produto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(ConvertEntityAndDTO.convertEntityToDto(product));
    }

    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        Product updatedProduct = productService.update(id, ConvertEntityAndDTO.convertDtoToEntity(productRequestDTO));
        return ResponseEntity.ok(ConvertEntityAndDTO.convertEntityToDto(updatedProduct));
    }

    @Operation(summary = "Excluir produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

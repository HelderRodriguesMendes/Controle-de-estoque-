package com.desafiotecnico.stock_service;

import com.desafiotecnico.stock_service.dto.request.ProductRequestDTO;
import com.desafiotecnico.stock_service.dto.response.ProductResponseDTO;
import com.desafiotecnico.stock_service.feignclient.ProductClient;
import com.desafiotecnico.stock_service.service.ProductStockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductStockServiceImplTest {

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private ProductStockServiceImpl productStockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validStock_shouldReturnProductRequestDTO_withLowStockFalse() {
        Long productId = 1L;

        ProductResponseDTO mockResponse = ProductResponseDTO.builder()
            .id(productId)
            .name("Notebook")
            .description("Notebook i7")
            .price(BigDecimal.valueOf(3500))
            .quantity(15) // maior que 10
            .build();

        when(productClient.getProductById(productId))
            .thenReturn(ResponseEntity.ok(mockResponse));

        ProductRequestDTO result = productStockService.validStock(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Notebook", result.getName());
        assertFalse(result.getLowStock()); // estoque não é baixo
        verify(productClient, times(1)).getProductById(productId);
    }

    @Test
    void validStock_shouldReturnProductRequestDTO_withLowStockTrue() {
        Long productId = 2L;

        ProductResponseDTO mockResponse = ProductResponseDTO.builder()
            .id(productId)
            .name("Mouse")
            .description("Mouse Gamer")
            .price(BigDecimal.valueOf(150.0))
            .quantity(5) // menor que 10
            .build();

        when(productClient.getProductById(productId))
            .thenReturn(ResponseEntity.ok(mockResponse));

        ProductRequestDTO result = productStockService.validStock(productId);

        assertNotNull(result);
        assertTrue(result.getLowStock()); // estoque baixo
        assertEquals("Mouse", result.getName());
        verify(productClient, times(1)).getProductById(productId);
    }

    @Test
    void validStock_shouldThrowException_whenProductNotFound() {
        Long productId = 3L;

        when(productClient.getProductById(productId))
            .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> productStockService.validStock(productId));

        assertTrue(exception.getMessage().contains("Failed to fetch product"));
        verify(productClient, times(1)).getProductById(productId);
    }

}

package com.desafiotecnico.product_service;

import com.desafiotecnico.product_service.dto.request.ProductSendMessageRequestDTO;
import com.desafiotecnico.product_service.entity.Product;
import com.desafiotecnico.product_service.enums.MessageException;
import com.desafiotecnico.product_service.exception.NotFoundException;
import com.desafiotecnico.product_service.message.KafkaProducerMessage;
import com.desafiotecnico.product_service.repository.ProductRepository;
import com.desafiotecnico.product_service.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private KafkaProducerMessage kafkaProducerMessage;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
            .id(1L)
            .name("Produto Teste")
            .build();
    }

    @Test
    void saveProduct_ShouldReturnSavedProduct() {
        when(productRepository.save(product)).thenReturn(product);

        Product saved = productService.save(product);

        assertNotNull(saved);
        assertEquals(product.getId(), saved.getId());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product found = productService.findById(1L);

        assertNotNull(found);
        assertEquals(product.getId(), found.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenProductNotFound_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.findById(1L));
        assertEquals(MessageException.PRODUCT_NOT_FOUND.getMensagem() + 1L, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findByName_WhenProductExists_ShouldReturnProductList() {
        List<Product> productList = Arrays.asList(product);
        when(productRepository.findByName("Produto Teste")).thenReturn(Optional.of(productList));

        List<Product> result = productService.findByName("Produto Teste");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());
    }

    @Test
    void findByName_WhenProductNotFound_ShouldThrowException() {
        when(productRepository.findByName("Produto Teste")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productService.findByName("Produto Teste"));
        assertEquals(MessageException.PRODUCT_NOT_FOUND.getMensagem() + "Produto Teste", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void updateProduct_ShouldSaveAndSendKafkaMessage() {
        Product updatedProduct = Product.builder().name("Produto Atualizado").build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.update(1L, updatedProduct);

        assertEquals(updatedProduct.getName(), result.getName());
        ArgumentCaptor<ProductSendMessageRequestDTO> captor = ArgumentCaptor.forClass(ProductSendMessageRequestDTO.class);
        verify(kafkaProducerMessage).sendMessageUpdate(captor.capture());
        assertEquals(updatedProduct.getId(), captor.getValue().getProductId());
    }

    @Test
    void deleteProduct_ShouldCallRepositoryDelete() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAll_ShouldReturnListOfProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        assertEquals(product.getId(), result.get(0).getId());
    }
}

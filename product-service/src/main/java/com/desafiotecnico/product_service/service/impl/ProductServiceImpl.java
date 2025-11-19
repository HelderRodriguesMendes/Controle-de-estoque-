package com.desafiotecnico.product_service.service.impl;

import com.desafiotecnico.product_service.dto.request.ProductSendMessageRequestDTO;
import com.desafiotecnico.product_service.dto.response.ProductResponseDTO;
import com.desafiotecnico.product_service.entity.Product;
import com.desafiotecnico.product_service.enums.MessageException;
import com.desafiotecnico.product_service.exception.NotFoundException;
import com.desafiotecnico.product_service.message.KafkaProducerMessage;
import com.desafiotecnico.product_service.repository.ProductRepository;
import com.desafiotecnico.product_service.service.ProductService;
import com.desafiotecnico.product_service.utils.ConvertEntityAndDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final KafkaProducerMessage kafkaProducerMessage;

    @Override
    public Product save(Product product) {
        log.info("starting save product: {}", product);
        Product savedProduct = productRepository.save(product);
        log.info("product saved successfully: {}", savedProduct);
        return savedProduct;
    }

    @Override
    public List<Product> findByName(String name) {
        log.info("starting find product by name: {}", name);
        Optional<List<Product>> optionalProductList = productRepository.findByName(name);
        if (!optionalProductList.isPresent()) {
            log.info("product not found: {}", name);
            throw new NotFoundException(
                MessageException.PRODUCT_NOT_FOUND.getMensagem()
                    + name, HttpStatus.NOT_FOUND
            );
        }
        return optionalProductList.get();
    }

    @Override
    public List<Product> findAll() {
        log.info("starting find all products");
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        log.info("starting find product by id: {}", id);
        Optional<Product> productOptional = productRepository.findById(id);
        if(!productOptional.isPresent()) {
            log.info("product not found: {}", id);
            throw new NotFoundException(
                MessageException.PRODUCT_NOT_FOUND.getMensagem()
                    + id, HttpStatus.NOT_FOUND
            );
        }
        log.info("product found: {}", productOptional.get());
        return productOptional.get();
    }

    @Override
    public Product update(Long id, Product product) {
        Product productSaved = findById(id);
        log.info("starting update product: {}", product);
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        log.info("Starting send message to kafka for product update: {}", updatedProduct);
        kafkaProducerMessage.sendMessageUpdate(new ProductSendMessageRequestDTO(updatedProduct.getId()));
        log.info("product updated successfully: {}", updatedProduct);
        return updatedProduct;
    }

    @Override
    public void delete(Long id) {
        findById(id);
        log.info("starting delete product by id: {}", id);
        productRepository.deleteById(id);
        log.info("product deleted successfully: {}", id);
    }

}

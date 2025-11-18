package com.desafiotecnico.product_service.service.impl;

import com.desafiotecnico.product_service.entity.Product;
import com.desafiotecnico.product_service.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product findByName(String name) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public Product update(Long id, Product product) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

package com.desafiotecnico.product_service.service;

import com.desafiotecnico.product_service.entity.Product;

import java.util.List;

public interface ProductService {
    public Product save(Product product);
    public List<Product> findByName(String name);
    public List<Product> findAll();
    public Product findById(Long id);
    public Product update(Long id, Product product);
    public void delete(Long id);
}

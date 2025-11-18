package com.desafiotecnico.product_service.repository;

import com.desafiotecnico.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<List<Product>> findByName(String name);
    public Optional<Product> findById(Long id);
}

package com.desafiotecnico.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable=false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable=false)
    private Integer quantity;

    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }
    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
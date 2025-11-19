package com.desafiotecnico.stock_service.service;

import com.desafiotecnico.stock_service.dto.request.ProductRequestDTO;

public interface ProductStockService {
    public ProductRequestDTO validStock(Long productId);
}

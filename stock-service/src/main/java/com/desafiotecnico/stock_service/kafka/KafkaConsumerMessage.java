package com.desafiotecnico.stock_service.kafka;

import com.desafiotecnico.stock_service.dto.request.ProductSendMessageRequestDTO;
import com.desafiotecnico.stock_service.service.ProductStockServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerMessage {

    private final ProductStockServiceImpl productStockService;

    @KafkaListener(topics = "product_topic_update", groupId = "store-post-group")
    public void listening(ProductSendMessageRequestDTO productSendMessageRequestDTO) {
        log.info("Received message to validate stock for product ID: {}", productSendMessageRequestDTO.getProductId());
        productStockService.validStock(productSendMessageRequestDTO.getProductId());
    }
}

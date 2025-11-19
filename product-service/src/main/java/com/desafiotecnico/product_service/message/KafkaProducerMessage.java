package com.desafiotecnico.product_service.message;

import com.desafiotecnico.product_service.dto.request.ProductSendMessageRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducerMessage {

    @Autowired
    private KafkaTemplate<String, ProductSendMessageRequestDTO> kafkaTemplate;

    private final String PRODUCT_TOPIC_UPDATE = "product_topic_update";

    public void sendMessageUpdate(ProductSendMessageRequestDTO sendMessageRequestDTO) {
        log.info("Sending message update to topic {}", PRODUCT_TOPIC_UPDATE);
        kafkaTemplate.send(PRODUCT_TOPIC_UPDATE, sendMessageRequestDTO);
    }
}

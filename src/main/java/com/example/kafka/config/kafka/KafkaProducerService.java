package com.example.kafka.config.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService<T> {
    private final KafkaTemplate<String, T> kafkaTemplate;
    public KafkaProducerService(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, T data) {
        kafkaTemplate.send(topic, data);
    }
}

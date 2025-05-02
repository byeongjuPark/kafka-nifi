package com.example.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListenerConsumer {

    @KafkaListener(topics = "nifi-to-spring", groupId = "spring-consumer")
    public void listen(String message) {
        log.info("Received from NiFi: {}", message);
    }
}


package com.example.kafka.config.kafka;

import com.example.kafka.config.redis.CommentCacheService;
import com.example.kafka.dto.CommentData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerConsumer {
    private final CommentCacheService commentCacheService;


    @KafkaListener(topics = "nifi-to-spring", groupId = "spring-consumer")
    public void listen(String message) {
        log.info("Received from NiFi: {}", message);
    }
    @KafkaListener(topics = "comment-data", groupId = "comment")
    public void listen2(CommentData comment) {
        commentCacheService.saveOrUpdateComment(comment);
        log.info("Received from Spring: {}", comment);
    }
}


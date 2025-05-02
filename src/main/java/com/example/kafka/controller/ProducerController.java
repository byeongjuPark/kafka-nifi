package com.example.kafka.controller;

import com.example.kafka.dto.CommentData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final KafkaTemplate<String, CommentData> kafkaTemplate;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody CommentData data) {

        kafkaTemplate.send("spring-to-nifi", data);
        return ResponseEntity.ok(data.getUser()+"_"+data.getComment());
    }
}

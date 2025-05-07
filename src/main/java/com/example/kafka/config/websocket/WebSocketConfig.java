package com.example.kafka.config.websocket;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @PostConstruct
    public void init() {
        System.out.println("[WebSocketConfig] ✅ WebSocket 설정이 로드되었습니다.");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 이 경로는 SockJS 연결용
                .setAllowedOriginPatterns("*") // CORS 문제 해결
                .withSockJS(); // SockJS 허용
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/comments"); // client.subscribe 용
        registry.setApplicationDestinationPrefixes("/app"); // publish 용
    }
}



package com.example.kafka.config.redis;

import com.example.kafka.dto.CommentData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CommentCacheService {

    private final String COMMENT_KEY_PREFIX = "comment:";
    private final String COMMENT_LIST_KEY = "comment:list";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private final SimpMessagingTemplate messagingTemplate;

    public CommentCacheService(RedisTemplate<String, Object> redisTemplate, SimpMessagingTemplate messagingTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
        this.messagingTemplate = messagingTemplate;
    }

    public void saveOrUpdateComment(CommentData comment) {
        String key = COMMENT_KEY_PREFIX + comment.getUuid();

        try {
            String json = objectMapper.writeValueAsString(comment);
            redisTemplate.opsForValue().set(key, json, Duration.ofMinutes(3));

            List<Object> existingUuids = redisTemplate.opsForList().range(COMMENT_LIST_KEY, 0, -1);
            if (existingUuids == null || !existingUuids.contains(comment.getUuid())) {
                redisTemplate.opsForList().rightPush(COMMENT_LIST_KEY, comment.getUuid());
            }

            // 리스트에 uuid 넣은 다음에 WebSocket 전송
            messagingTemplate.convertAndSend("/comments/comment", getAllComments());
            log.info("Sending {} comments", getAllComments().size());

        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }
    }

    public List<CommentData> getAllComments() {
        List<Object> uuids = redisTemplate.opsForList().range(COMMENT_LIST_KEY, 0, -1);
        if (uuids == null) return Collections.emptyList();

        List<CommentData> comments = new ArrayList<>();
        for (Object uuidObj : uuids) {
            String key = COMMENT_KEY_PREFIX + uuidObj;
            Object json = redisTemplate.opsForValue().get(key);
            if (json != null) {
                try {
                    comments.add(objectMapper.readValue(json.toString(), CommentData.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return comments;
    }
}

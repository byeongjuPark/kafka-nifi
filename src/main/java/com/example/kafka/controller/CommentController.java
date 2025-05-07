package com.example.kafka.controller;

import com.example.kafka.config.redis.CommentCacheService;
import com.example.kafka.dto.BlogComment;
import com.example.kafka.dto.CommentData;
import com.example.kafka.dto.NewsComment;
import com.example.kafka.config.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final KafkaProducerService<BlogComment> blogCommentProducerService;
    private final KafkaProducerService<NewsComment> newsCommentProducerService;

    private final CommentCacheService commentCacheService;

    @PostMapping("/blog/comment")
    public ResponseEntity<?> insertBlogComment(@RequestBody BlogComment comment) {
        UUID uuid = UUID.randomUUID();
        comment.setUuid(uuid.toString());
        blogCommentProducerService.send("blog-comment", comment);
        return new ResponseEntity<>("su", HttpStatus.OK);
    }
    @PostMapping("/news/comment")
    public ResponseEntity<?> insertNewsComment(@RequestBody NewsComment comment) {
        UUID uuid = UUID.randomUUID();
        comment.setUuid(uuid.toString());
        newsCommentProducerService.send("news-comment", comment);
        return new ResponseEntity<>("su", HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getComments() {
        List<CommentData> results = commentCacheService.getAllComments();
        return ResponseEntity.ok(results);
    }

    @PostMapping("/comment/like")
    public ResponseEntity<?> likeComment(@RequestBody CommentData comment) {

        int count = comment.getLikeCount();
        comment.setLikeCount(++count);

        commentCacheService.saveOrUpdateComment(comment);
        return ResponseEntity.ok(comment);
    }
}

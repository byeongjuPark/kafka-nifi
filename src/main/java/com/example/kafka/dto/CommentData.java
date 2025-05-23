package com.example.kafka.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentData {
    private String uuid;
    private String user;
    private String comment;
    private String type;
    private int likeCount;
}

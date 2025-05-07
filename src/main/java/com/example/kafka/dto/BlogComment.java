package com.example.kafka.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogComment {
    private String uuid;
    private String user;
    private String comment;
    private int likeCount;
}

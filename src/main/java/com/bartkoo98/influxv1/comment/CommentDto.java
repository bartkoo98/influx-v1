package com.bartkoo98.influxv1.comment;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}

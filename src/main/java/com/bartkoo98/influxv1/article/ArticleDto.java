package com.bartkoo98.influxv1.article;

import com.bartkoo98.influxv1.comment.CommentDto;
import lombok.Data;

import java.util.Set;

@Data
class ArticleDto {

    private Long id;
    private String title;

    private String content;
    private Set<CommentDto> comments;
}

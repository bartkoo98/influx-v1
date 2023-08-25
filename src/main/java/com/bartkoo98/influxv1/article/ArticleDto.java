package com.bartkoo98.influxv1.article;

import com.bartkoo98.influxv1.comment.CommentDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
class ArticleDto {

    private Long id;
    @NotEmpty
    @Size(min = 4, message = "Article title should have at least 4 characters.")
    private String title;
    @NotEmpty
    @Size(min = 25,message = "Article content should have at least 25 characters." )
    private String content;
    private Set<CommentDto> comments;
}
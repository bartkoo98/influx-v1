package com.bartkoo98.influxv1.article;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {

    private Long id;
    @NotEmpty
    @Size(min = 4, message = "Article title should have at least 4 characters.")
    private String title;
    @NotEmpty
    @Size(min = 40,message = "Article content should have at least 40 characters." )
    private String content;
    private LocalDateTime createdAt;

    private Long categoryId;
}

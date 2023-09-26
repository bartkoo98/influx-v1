package com.bartkoo98.influxv1.article;

import com.bartkoo98.influxv1.category.Category;

import java.time.LocalDateTime;

class ArticleMapper {

    public static ArticleDto mapToArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(LocalDateTime.now())
                .categoryId(article.getCategory().getId())
                .build();
    }

    public static Article mapToArticle(ArticleDto articleDto) {
        Category category = new Category();
        category.setId(articleDto.getId());
        return Article.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .createdAt(LocalDateTime.now())
                .category(category)
                .build();
    }
}

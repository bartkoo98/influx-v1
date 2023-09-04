package com.bartkoo98.influxv1.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAll();
    List<Article> findByCategoryId(Long categoryId);
}

package com.bartkoo98.influxv1.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
}

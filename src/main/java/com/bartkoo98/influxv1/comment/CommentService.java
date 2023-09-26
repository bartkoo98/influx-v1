package com.bartkoo98.influxv1.comment;

import com.bartkoo98.influxv1.article.Article;
import com.bartkoo98.influxv1.article.ArticleRepository;
import com.bartkoo98.influxv1.exception.APIException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }
// todo podzielic mozliwosc tworzenia komentarzy dla zalogowanych i niezalogowanych uzytkownikow
    public CommentDto createComment(Long articleId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Article article = articleRepository.findById(articleId).orElseThrow();

        comment.setArticle(article);
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    public List<CommentDto> getAllCommentsForArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(this::mapToDto)
                .toList();
    }

    public CommentDto getCommentForArticle(Long articleId, Long commentId) {
        Comment comment = getCommentAssignedToArticleIfNotBelongThrowException(articleId, commentId);
        return mapToDto(comment);
    }

    

    public CommentDto updateComment(Long articleId, Long commentId, CommentDto commentRequest) {
        Comment comment = getCommentAssignedToArticleIfNotBelongThrowException(articleId, commentId);

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    public void deleteCommentById(Long articleId, Long commentId) {
        Comment comment = getCommentAssignedToArticleIfNotBelongThrowException(articleId, commentId);
        commentRepository.delete(comment);
    }

    Comment getCommentAssignedToArticleIfNotBelongThrowException(Long articleId, Long commentId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if(!comment.getArticle().getId().equals(article.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}

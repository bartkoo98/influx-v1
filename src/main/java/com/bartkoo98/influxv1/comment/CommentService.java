package com.bartkoo98.influxv1.comment;

import com.bartkoo98.influxv1.article.Article;
import com.bartkoo98.influxv1.article.ArticleRepository;
import com.bartkoo98.influxv1.exception.APIException;
import com.bartkoo98.influxv1.exception.ResourceNotFoundException;
import com.bartkoo98.influxv1.exception.UnauthorizedException;
import com.bartkoo98.influxv1.user.User;
import com.bartkoo98.influxv1.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bartkoo98.influxv1.comment.CommentMapper.mapToComment;
import static com.bartkoo98.influxv1.comment.CommentMapper.mapToCommentDto;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          ArticleRepository articleRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }
// todo podzielic mozliwosc tworzenia komentarzy dla zalogowanych i niezalogowanych uzytkownikow
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userRepository.findByUsernameOrEmail(username, username).orElse(null);
        }
        throw new UnauthorizedException();
    }

    public CommentDto createComment(Long articleId, CommentDto commentDto) {
        User user = getAuthenticatedUser();
            if (user != null) {
                Comment comment = mapToComment(commentDto);
                Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

                comment.setArticle(article);
                comment.setUser(user);
                Comment newComment = commentRepository.save(comment);

                return mapToCommentDto(newComment);
            } else {
                throw new APIException(HttpStatus.NOT_FOUND, "Something went wrong! User not exists.");
            }
        }


    public List<CommentDto> getAllCommentsForArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .toList();
    }

    public CommentDto getCommentForArticle(Long articleId, Long commentId) {
        Comment comment = getCommentAssignedToArticleIfNotBelongThrowException(articleId, commentId);
        return mapToCommentDto(comment);
    }


    public CommentDto updateComment(Long articleId, Long commentId, CommentDto commentDto) {
        User user = getAuthenticatedUser();
        if(user != null) {
            Comment comment = getCommentAssignedToArticleIfNotBelongThrowException(articleId, commentId);
            if (!comment.getUser().getId().equals(user.getId())) {
                throw new UnauthorizedException();
            }
            comment.setBody(commentDto.getBody());
            Comment updatedComment = commentRepository.save(comment);

            return mapToCommentDto(updatedComment);
        }
        throw new UnauthorizedException();
    }

    public void deleteCommentById(Long articleId, Long commentId) {
        Comment comment = getCommentAssignedToArticleIfNotBelongThrowException(articleId, commentId);
        commentRepository.delete(comment);
    }

    Comment getCommentAssignedToArticleIfNotBelongThrowException(Long articleId, Long commentId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getArticle().getId().equals(article.getId())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return comment;
    }


}

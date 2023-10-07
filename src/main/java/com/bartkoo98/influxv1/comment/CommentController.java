package com.bartkoo98.influxv1.comment;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long articleId,
                                                    @Valid @RequestBody CommentDto commentDto){
        CommentDto newCommentDto = commentService.createComment(articleId, commentDto);
        return new ResponseEntity<>(newCommentDto, HttpStatus.CREATED);
    }

    @GetMapping("/articles/{articleId}/comments")
    public List<CommentDto> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.getAllCommentsForArticle(articleId);
    }

    @GetMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByArticleId(@PathVariable(value = "articleId") Long articleId,
                                                            @PathVariable(value = "commentId")Long commentId) {
        CommentDto commentForArticle = commentService.getCommentForArticle(articleId, commentId);
        return new ResponseEntity<>(commentForArticle, HttpStatus.OK);
    }
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "articleId") Long articleId,
                                                    @PathVariable(value = "commentId")Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(articleId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "articleId") Long articleId,
                                                @PathVariable(value = "commentId")Long commentId) {
        commentService.deleteCommentById(articleId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}

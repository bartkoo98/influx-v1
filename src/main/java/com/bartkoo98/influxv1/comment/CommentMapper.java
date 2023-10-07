package com.bartkoo98.influxv1.comment;

public class CommentMapper {


    public static CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .build();
    }

    public static Comment mapToComment(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .body(commentDto.getBody())
                .build();
    }

}

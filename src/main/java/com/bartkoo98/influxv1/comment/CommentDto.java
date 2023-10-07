package com.bartkoo98.influxv1.comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class CommentDto {
    private Long id;
    @NotEmpty
    private String body;
}

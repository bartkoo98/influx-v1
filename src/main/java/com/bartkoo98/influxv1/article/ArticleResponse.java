package com.bartkoo98.influxv1.article;

import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
class ArticleResponse {
    private List<ArticleDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
}

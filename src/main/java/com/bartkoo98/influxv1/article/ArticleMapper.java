package com.bartkoo98.influxv1.article;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
// todo zmienic na mapper recznie pisany
@Component
class ArticleMapper {
    private static ModelMapper modelMapper;

    public ArticleMapper(ModelMapper modelMapper) {
        ArticleMapper.modelMapper = modelMapper;
    }

    static ArticleDto mapToDTO(Article article) {
        return modelMapper.map(article, ArticleDto.class);
    }

    static Article mapToEntity(ArticleDto articleDto) {
        return modelMapper.map(articleDto, Article.class);
    }
}

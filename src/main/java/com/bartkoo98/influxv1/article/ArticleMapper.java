package com.bartkoo98.influxv1.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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

package com.bartkoo98.influxv1.article;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ArticleService {
    private final ArticleRepository articleRepository;

    private final ModelMapper modelMapper;

    public ArticleService(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    public ArticleDto createArticle(ArticleDto articleDto) {
        Article article = mapToEntity(articleDto);
        Article newArticle = articleRepository.save(article);
        return mapToDTO(newArticle);
    }

    public ArticleResponse getAllArticles(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> allArticles = articleRepository.findAll(pageable);

        List<Article> listOfArticles = allArticles.getContent();

        List<ArticleDto> content = listOfArticles.stream().map(this::mapToDTO).toList();

        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setContent(content);
        articleResponse.setPageNo(allArticles.getNumber());
        articleResponse.setPageSize(allArticles.getSize());
        articleResponse.setTotalElements(allArticles.getTotalElements());
        articleResponse.setTotalPages(allArticles.getTotalPages());
        articleResponse.setLast(allArticles.isLast());
        return articleResponse;
    }

    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        return mapToDTO(article);
    }

    public ArticleDto updateArticle(ArticleDto articleDto, Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());

        Article updatedArticle = articleRepository.save(article);
        return mapToDTO(updatedArticle);
    }

    public void deleteArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        articleRepository.delete(article);
    }



    private ArticleDto mapToDTO(Article article) {
        return modelMapper.map(article, ArticleDto.class);
    }

    private Article mapToEntity(ArticleDto articleDto) {
        return modelMapper.map(articleDto, Article.class);
    }
}

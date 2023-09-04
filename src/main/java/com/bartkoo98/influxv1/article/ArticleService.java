package com.bartkoo98.influxv1.article;

import com.bartkoo98.influxv1.category.Category;
import com.bartkoo98.influxv1.category.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bartkoo98.influxv1.article.ArticleMapper.mapToDTO;
import static com.bartkoo98.influxv1.article.ArticleMapper.mapToEntity;

@Service
class ArticleService {
    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;

    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    public ArticleDto createArticle(ArticleDto articleDto) {
        Category category = categoryRepository.findById(articleDto.getCategoryId()).orElseThrow();
        Article article = mapToEntity(articleDto);
        article.setCategory(category);
        Article newArticle = articleRepository.save(article);
        return mapToDTO(newArticle);
    }

    public ArticleResponse getAllArticles(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> allArticles = articleRepository.findAll(pageable);

        List<Article> listOfArticles = allArticles.getContent();

        List<ArticleDto> content = listOfArticles.stream().map(ArticleMapper::mapToDTO).toList();

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

    public List<ArticleDto> getArticlesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        List<Article> articles = articleRepository.findByCategoryId(category.getId());
        return articles.stream().map(ArticleMapper::mapToDTO).toList();
    }

    public ArticleDto updateArticle(ArticleDto articleDto, Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        Category category = categoryRepository.findById(articleDto.getCategoryId()).orElseThrow();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setCategory(category);

        Article updatedArticle = articleRepository.save(article);
        return mapToDTO(updatedArticle);
    }

    public void deleteArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        articleRepository.delete(article);
    }



}

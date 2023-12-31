package com.bartkoo98.influxv1.article;

import com.bartkoo98.influxv1.category.Category;
import com.bartkoo98.influxv1.category.CategoryRepository;
import com.bartkoo98.influxv1.email.EmailService;
import com.bartkoo98.influxv1.exception.ResourceAlreadyExistsException;
import com.bartkoo98.influxv1.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bartkoo98.influxv1.article.ArticleMapper.mapToArticle;
import static com.bartkoo98.influxv1.article.ArticleMapper.mapToArticleDto;

@Service
class ArticleService {
    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;
    private final EmailService emailService;

    public ArticleService(ArticleRepository articleRepository,
                          CategoryRepository categoryRepository,
                          EmailService emailService) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.emailService = emailService;
    }

    public ArticleDto createArticle(ArticleDto articleDto) {
            Category category = categoryRepository.findById(articleDto.getCategoryId()).orElseThrow();
            Optional<Article> savedArticle = articleRepository.findByTitle(articleDto.getTitle());
            if(savedArticle.isPresent()) {
                throw new ResourceAlreadyExistsException("Article", articleDto.getTitle());
            }
            Article article = mapToArticle(articleDto);
            article.setCategory(category);
            Article newArticle = articleRepository.save(article);
            String id = newArticle.getId() + "";
            String title = newArticle.getTitle();
            List<String> emailsOfSubscribers = emailService.getEmailsOfSubscribers();
            emailService.sendNotificationAboutNewArticle(emailsOfSubscribers, title, id);
            return mapToArticleDto(newArticle);
    }


    public ArticleResponse getAllArticles(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Article> allArticles = articleRepository.findAll(pageable);

        List<Article> listOfArticles = allArticles.getContent();

        List<ArticleDto> content = listOfArticles.stream().map(ArticleMapper::mapToArticleDto).toList();

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
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        return mapToArticleDto(article);
    }

    public List<ArticleDto> getArticlesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Article> articles = articleRepository.findByCategoryId(category.getId());
        return articles.stream().map(ArticleMapper::mapToArticleDto).toList();
    }

    public ArticleDto updateArticle(ArticleDto articleDto, Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        Category category = categoryRepository.findById(articleDto.getCategoryId()).orElseThrow();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setCategory(category);

        Article updatedArticle = articleRepository.save(article);
        return mapToArticleDto(updatedArticle);
    }

    public void deleteArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        articleRepository.delete(article);
    }



}

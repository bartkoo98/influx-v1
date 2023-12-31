package com.bartkoo98.influxv1.article;

import com.bartkoo98.influxv1.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@Valid @RequestBody ArticleDto articleDto) {
        ArticleDto createdArticle = articleService.createArticle(articleDto);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping
    public ArticleResponse getAllArticles(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
            ) {
        return articleService.getAllArticles(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        ArticleDto article = articleService.getArticleById(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ArticleDto>> getArticlesByCategory(@PathVariable Long categoryId) {
        List<ArticleDto> articlesByCategory = articleService.getArticlesByCategory(categoryId);
        return new ResponseEntity<>(articlesByCategory, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@Valid @RequestBody ArticleDto articleDto,
                                                    @PathVariable Long id) {
        ArticleDto updatedArticle = articleService.updateArticle(articleDto, id);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
       articleService.deleteArticleById(id);
       return new ResponseEntity<>("Article entity deleted successfully.", HttpStatus.OK);
    }
}

package com.bartkoo98.influxv1.article;

import com.bartkoo98.influxv1.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto) {
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

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@RequestBody ArticleDto articleDto,
                                                    @PathVariable Long id) {
        ArticleDto updatedArticle = articleService.updateArticle(articleDto, id);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
       articleService.deleteArticleById(id);
       return new ResponseEntity<>("Article entity deleted successfully.", HttpStatus.OK);
    }
}

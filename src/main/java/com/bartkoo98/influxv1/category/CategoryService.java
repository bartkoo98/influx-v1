package com.bartkoo98.influxv1.category;

import com.bartkoo98.influxv1.exception.APIException;
import com.bartkoo98.influxv1.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bartkoo98.influxv1.category.CategoryMapper.mapToCategory;
import static com.bartkoo98.influxv1.category.CategoryMapper.mapToCategoryDto;

@Service
class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Optional<Category> savedCategory = categoryRepository.findByName(categoryDto.getName());
        if(savedCategory.isPresent()) {
            throw new APIException("Category already exists with given name: " + categoryDto.getName());
        }
        Category category = mapToCategory(categoryDto);
        Category newCategory = categoryRepository.save(category);
        return mapToCategoryDto(newCategory);
    }

    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return mapToCategoryDto(category);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::mapToCategoryDto)
                .toList();
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        category.setName(categoryDto.getName());

        Category updatedCategory = categoryRepository.save(category);
        return mapToCategoryDto(updatedCategory);
    }

    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }


}

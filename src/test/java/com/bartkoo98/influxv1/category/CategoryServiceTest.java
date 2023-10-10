package com.bartkoo98.influxv1.category;

import com.bartkoo98.influxv1.exception.APIException;
import com.bartkoo98.influxv1.exception.ResourceAlreadyExistsException;
import com.bartkoo98.influxv1.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;
    private Category category;
    private CategoryDto categoryDto;
    @BeforeEach
    public void setup() {
        category = Category.builder()
                .id(1L)
                .name("Technology")
                .build();
        categoryDto = CategoryDto.builder()
                .id(1L)
                .name("Technology")
                .build();
    }

    @Test
    public void categoryService_saveCategory_returnCategoryDto() {
        given(categoryRepository.findByName(categoryDto.getName()))
                .willReturn(Optional.empty());
        given(categoryRepository.save(any(Category.class))).willReturn(category);

        CategoryDto savedCategory = categoryService.saveCategory(categoryDto);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isEqualTo(1L);
        assertThat(savedCategory.getName()).isEqualTo("Technology");
    }

    @Test
    public void categoryService_saveCategory_throwsException() {
        given(categoryRepository.findByName(categoryDto.getName()))
                .willReturn(Optional.of(category));

        Throwable thrownException = catchThrowable(() -> categoryService.saveCategory(categoryDto));

        assertThat(thrownException)
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Category with name 'Technology' already exists.");
    }


    @Test
    public void categoryService_getAllCategories_returnListOfCategoryDto() {
        Category category2 = Category.builder()
                .id(2L)
                .name("Science")
                .build();
        List<Category> categoryList = List.of(category, category2);
        given(categoryRepository.findAll()).willReturn(categoryList);

        List<CategoryDto> result = categoryService.getAllCategories();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Technology");
        assertThat(result.get(1).getName()).isEqualTo("Science");
    }
    @Test
    public void categoryService_getCategoryById_returnCategoryDto() {

        given(categoryRepository.findById(category.getId())).willReturn(Optional.ofNullable(category));

        CategoryDto savedCategory = categoryService.getCategoryById(category.getId());

        assertThat(savedCategory).isNotNull();
    }

    @Test
    public void categoryService_getCategoryById_throwsException() {
        long nonExistentId = 123L;
        given(categoryRepository.findById(nonExistentId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategoryById(nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found with id: 123");
    }

    @Test
    public void categoryService_updateCategory_returnCategoryDto() {
        given(categoryRepository.findById(category.getId())).willReturn(Optional.ofNullable(category));
        given(categoryRepository.save(category)).willReturn(category);

        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, category.getId());

        assertThat(updateCategory).isNotNull();
    }
    @Test
    public void categoryService_updateCategory_throwsException() {
        long nonExistentId = 123L;
        given(categoryRepository.findById(nonExistentId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(categoryDto, nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found with id: 123");
    }

    @Test
    public void categoryService_deleteCategoryById_returnVoid() {
        given(categoryRepository.findById(category.getId())).willReturn(Optional.ofNullable(category));

        doNothing().when(categoryRepository).delete(category);

        assertAll(() -> categoryService.deleteCategoryById(category.getId()));
    }
    @Test
    public void categoryService_deleteCategoryById_throwsException() {

        long nonExistentId = 123L;
        given(categoryRepository.findById(nonExistentId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.deleteCategoryById(nonExistentId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found with id: 123");
    }


}

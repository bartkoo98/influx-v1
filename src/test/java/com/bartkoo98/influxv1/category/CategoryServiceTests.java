package com.bartkoo98.influxv1.category;

import com.bartkoo98.influxv1.exception.APIException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class CategoryServiceTests {

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

    @DisplayName("JUnit test for saveCategory method")
    @Test
    public void categoryService_saveCategory_returnCategoryDto() {
        // given
        given(categoryRepository.findByName(categoryDto.getName()))
                .willReturn(Optional.empty());
        given(categoryRepository.save(any(Category.class))).willReturn(category);

        // when
        CategoryDto savedCategory = categoryService.saveCategory(categoryDto);
        // then
        assertThat(savedCategory).isNotNull();
    }

    @DisplayName("JUnit test for saveCategory method which throws exception")
    @Test
    public void categoryService_saveCategory_throwsException() {
        // given
        given(categoryRepository.findByName(categoryDto.getName()))
                .willReturn(Optional.of(category));

        // when
        Throwable thrownException = catchThrowable(() -> categoryService.saveCategory(categoryDto));
        // then
        assertThat(thrownException)
                .isInstanceOf(APIException.class)
                .hasMessageContaining("Category already exists with given name: Technology");
    }


    @DisplayName("JUnit test for getAllCategories method")
    @Test
    public void categoryService_getAllCategories_returnListOfCategoryDto() {
        // given
        Category category2 = Category.builder()
                .id(2L)
                .name("Science")
                .build();
        List<Category> categoryList = List.of(category, category2);

        given(categoryRepository.findAll()).willReturn(categoryList);
        // when
        List<CategoryDto> result = categoryService.getAllCategories();
        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Technology");
        assertThat(result.get(1).getName()).isEqualTo("Science");
    }
    @DisplayName("JUnit test for getCategoryById method")
    @Test
    public void categoryService_getCategoryById_returnCategoryDto() {
        // given
        given(categoryRepository.findById(category.getId())).willReturn(Optional.ofNullable(category));
        // when
        CategoryDto savedCategory = categoryService.getCategoryById(category.getId());
        // then
        assertThat(savedCategory).isNotNull();
    }

    @DisplayName("JUnit test for getCategoryById method which throws exception")
    @Test
    public void categoryService_getCategoryById_throwsException() {
        // given
        long nonExistentId = 123L;
        given(categoryRepository.findById(nonExistentId)).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> categoryService.getCategoryById(nonExistentId))
                .isInstanceOf(APIException.class)
                .hasMessageContaining("Category with the given id does not exist.");
    }

    @DisplayName("JUnit test for updateCategory method")
    @Test
    public void categoryService_updateCategory_returnCategoryDto() {
        // given
        given(categoryRepository.findById(category.getId())).willReturn(Optional.ofNullable(category));
        given(categoryRepository.save(category)).willReturn(category);

        // when
        CategoryDto updateCategory = categoryService.updateCategory(categoryDto, category.getId());

        // then
        assertThat(updateCategory).isNotNull();
    }
    @DisplayName("JUnit test for updateCategory method which throws exception")
    @Test
    public void categoryService_updateCategory_throwsException() {
        // given
        long nonExistentId = 123L;
        given(categoryRepository.findById(nonExistentId)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> categoryService.updateCategory(categoryDto, nonExistentId))
                .isInstanceOf(APIException.class)
                .hasMessageContaining("Category with the given id does not exist.");
    }

    @DisplayName("JUnit test for deleteCategoryById method")
    @Test
    public void categoryService_deleteCategoryById_returnVoid() {
        // given
        given(categoryRepository.findById(category.getId())).willReturn(Optional.ofNullable(category));

        // when
        doNothing().when(categoryRepository).delete(category);
        // then
        assertAll(() -> categoryService.deleteCategoryById(category.getId()));
    }
    @DisplayName("JUnit test for deleteCategoryById method which throws exception")
    @Test
    public void categoryService_deleteCategoryById_throwsException() {
        // given
        long nonExistentId = 123L;
        given(categoryRepository.findById(nonExistentId)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> categoryService.deleteCategoryById(nonExistentId))
                .isInstanceOf(APIException.class)
                .hasMessageContaining("Category with the given id does not exist.");
    }


}

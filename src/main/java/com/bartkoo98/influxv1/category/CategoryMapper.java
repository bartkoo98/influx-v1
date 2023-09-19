package com.bartkoo98.influxv1.category;

public class CategoryMapper {


    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(), category.getName()
        );
    }

    public static Category mapToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setId(categoryDto.getId());
        return category;
    }


}

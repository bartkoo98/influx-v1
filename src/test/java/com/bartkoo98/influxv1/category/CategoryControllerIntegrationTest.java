package com.bartkoo98.influxv1.category;

import com.bartkoo98.influxv1.BaseIT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryControllerIntegrationTest extends BaseIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        categoryRepository.deleteAll();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void addCategoryForAdminReturn200IsCreated() throws Exception{
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();

        ResultActions response = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Technology"));
    }

    @Test
    @WithAnonymousUser
    public void addCategoryForNotLoggedInUserThrows401Unauthorized() throws Exception{
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();

        ResultActions response = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        response.andDo(print())
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void addCategoryForAdminThrows409ResourceAlreadyExistsException() throws Exception{
        Category existingCategory = new Category();
        existingCategory.setName("Technology");
        categoryRepository.save(existingCategory);

        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();

        ResultActions response = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        response.andDo(print())
                .andExpect(status().isConflict());
    }
    @Test
    public void getCategoryByIdReturnThisCategoryAnd200IsOk() throws Exception{
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();
        Category savedCategory = categoryRepository.save(CategoryMapper.mapToCategory(categoryDto));

        ResultActions response = mockMvc.perform(get("/api/categories/{id}", savedCategory.getId()));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDto.getName()));
    }
    @Test
    public void getCategoryByIdThrows404ResourceNotFoundException() throws Exception{
        Long nonExistingCategoryId = 100L;


        ResultActions response = mockMvc.perform(get("/api/categories/{id}", nonExistingCategoryId));

        response.andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void getAllCategories() throws Exception{
        CategoryDto categoryDto1 = CategoryDto.builder()
                .name("Technology")
                .build();
        CategoryDto categoryDto2 = CategoryDto.builder()
                .name("Health")
                .build();
        categoryRepository.save(CategoryMapper.mapToCategory(categoryDto1));
        categoryRepository.save(CategoryMapper.mapToCategory(categoryDto2));

        ResultActions response = mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Technology"))
                .andExpect(jsonPath("$[1].name").value("Health"));

    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteCategoryForAdminReturn200() throws Exception{
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();
        Category savedCategory = categoryRepository.save(CategoryMapper.mapToCategory(categoryDto));

        ResultActions response = mockMvc.perform(delete("/api/categories/{id}", savedCategory.getId()));

        response.andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteCategoryForAdminThrows404ResourceNotFoundException() throws Exception{
        Long nonExistingCategoryId = 100L;

        ResultActions response = mockMvc.perform(delete("/api/categories/{id}", nonExistingCategoryId));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    @WithAnonymousUser
    public void deleteCategoryForNotLoggedInUserThrows401Unauthorized() throws Exception{
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();
        Category savedCategory = categoryRepository.save(CategoryMapper.mapToCategory(categoryDto));

        ResultActions response = mockMvc.perform(delete("/api/categories/{id}", savedCategory.getId()));

        response.andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateCategoryForAdminReturn200() throws Exception{
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();
        Category savedCategory = categoryRepository.save(CategoryMapper.mapToCategory(categoryDto));

        CategoryDto updatedCategoryDto = CategoryDto.builder()
                .name("Science")
                .build();
        Category updatedSavedCategory = categoryRepository.save(CategoryMapper.mapToCategory(updatedCategoryDto));


        ResultActions response = mockMvc.perform(put("/api/categories/{id}", savedCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSavedCategory)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Science"));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateCategoryForAdminThrows404ResourceNotFoundException() throws Exception{
        Long nonExistingCategoryId = 100L;
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();
        categoryRepository.save(CategoryMapper.mapToCategory(categoryDto));

        CategoryDto updatedCategoryDto = CategoryDto.builder()
                .name("Science")
                .build();
        Category updatedSavedCategory = categoryRepository.save(CategoryMapper.mapToCategory(updatedCategoryDto));


        ResultActions response = mockMvc.perform(put("/api/categories/{id}", nonExistingCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSavedCategory)));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    @WithAnonymousUser
    public void updateCategoryForNotLoggedInUserThrows401Unauthorized() throws Exception{
        CategoryDto categoryDto = CategoryDto.builder()
                .name("Technology")
                .build();
        Category savedCategory = categoryRepository.save(CategoryMapper.mapToCategory(categoryDto));

        CategoryDto updatedCategoryDto = CategoryDto.builder()
                .name("Science")
                .build();
        Category updatedSavedCategory = categoryRepository.save(CategoryMapper.mapToCategory(updatedCategoryDto));


        ResultActions response = mockMvc.perform(put("/api/categories/{id}", savedCategory.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSavedCategory)));

        response.andDo(print())
                .andExpect(status().isUnauthorized());
    }

}

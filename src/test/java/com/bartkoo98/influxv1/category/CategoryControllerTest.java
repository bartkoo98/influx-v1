package com.bartkoo98.influxv1.category;

import com.bartkoo98.influxv1.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// todo przetestowac autoryzacje user/admin dla post, put, delete

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ObjectMapper objectMapper;
    private CategoryDto categoryDto;

    @BeforeEach
    public void setup() {
        categoryDto = CategoryDto.builder()
                .id(1L)
                .name("Technology")
                .build();;

    }

    @Test
    public void categoryController_addCategory_returnCreated() throws Exception{
        given(categoryService.saveCategory(any()))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(categoryDto.getName())));
    }

    @Test
    public void categoryController_getAllCategories_returnListOfCategoryDto() throws Exception {
        CategoryDto categoryDto2 = new CategoryDto(2L, "Science");
        List<CategoryDto> listOfCategories = new ArrayList<>();
        listOfCategories.add(categoryDto);
        listOfCategories.add(categoryDto2);
        given(categoryService.getAllCategories()).willReturn(listOfCategories);

        ResultActions response = mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON));


        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(listOfCategories.size())));
    }

    @Test
    public void categoryController_getCategoryById_returnCategoryDto() throws Exception{
        given(categoryService.getCategoryById(1L)).willReturn(categoryDto);

        ResultActions response = mockMvc.perform(get("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(categoryDto.getName())));
    }


    @Test
    public void categoryController_deleteCategory_returnString() throws Exception{
        doNothing().when(categoryService).deleteCategoryById(1L);

        ResultActions response = mockMvc.perform(delete("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }
}

package com.exam.controller;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.testmanagement.controllers.CategoryController;
import com.testmanagement.models.Category;
import com.testmanagement.response.SuccessResponse;
import com.testmanagement.services.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void testCreateCategory() {
        Category category = new Category(1,"Test Category", "Test Description");
       when(categoryService.createCategory(category)).thenReturn(category);

        ResponseEntity<SuccessResponse> response = categoryController.createCategory(category);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Category created successfully", response.getBody());
        verify(categoryService, times(1)).createCategory(category);
    }

    @Test
    void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add( new Category(1, "Category 1", "Description 1"));
        categories.add( new Category(2,"Category 2", "Description 2"));
        
        when(categoryService.getAllCategories()).thenReturn(categories);

        ResponseEntity<SuccessResponse> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testGetCategoryById() {
        int categoryId = 1;
        Category category = new Category(1,"Test Category", "Test Description");
        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        ResponseEntity<SuccessResponse> response = categoryController.getCategoryById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    void testDeleteCategoryById() {
        int categoryId = 1;
        when(categoryService.deleteCategoryByID(categoryId)).thenReturn(true);

        ResponseEntity<SuccessResponse> response = categoryController.deleteCategoryById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody());
        verify(categoryService, times(1)).deleteCategoryByID(categoryId);
    }

    @Test
    void testUpdateCategoryById() {
        int categoryId = 1;
        Category updatedCategory = new Category(1,"Updated Category", "Updated Description");
        when(categoryService.updateCategoryById(categoryId, updatedCategory)).thenReturn(updatedCategory);

        ResponseEntity<SuccessResponse> response = categoryController.updateCategoryById(categoryId, updatedCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category updated successfully", response.getBody());
        verify(categoryService, times(1)).updateCategoryById(eq(categoryId), any(Category.class));
    }
}

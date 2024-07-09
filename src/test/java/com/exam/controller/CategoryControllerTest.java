package com.exam.controller;
import com.exam.controllers.CategoryController;
import com.exam.models.Category;
import com.exam.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    public void testCreateCategory() {
        Category category = new Category(1,"Test Category", "Test Description");
       when(categoryService.createCategory(category)).thenReturn(category);

        ResponseEntity<String> response = categoryController.createCategory(category);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Category created successfully", response.getBody());
        verify(categoryService, times(1)).createCategory(category);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add( new Category(1, "Category 1", "Description 1"));
        categories.add( new Category(2,"Category 2", "Description 2"));
        
        when(categoryService.getAllCategoryInfo()).thenReturn(categories);

        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
        verify(categoryService, times(1)).getAllCategoryInfo();
    }

    @Test
    public void testGetCategoryById() {
        int categoryId = 1;
        Category category = new Category(1,"Test Category", "Test Description");
        when(categoryService.getCategoryInfo(categoryId)).thenReturn(Optional.of(category));

        ResponseEntity<Category> response = categoryController.getCategoryById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).getCategoryInfo(categoryId);
    }

    @Test
    public void testDeleteCategoryById() {
        int categoryId = 1;
        when(categoryService.deleteCategoryByID(categoryId)).thenReturn(true);

        ResponseEntity<String> response = categoryController.deleteCategoryById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody());
        verify(categoryService, times(1)).deleteCategoryByID(categoryId);
    }

    @Test
    public void testUpdateCategoryById() {
        int categoryId = 1;
        Category updatedCategory = new Category(1,"Updated Category", "Updated Description");
        when(categoryService.updateCategoryById(eq(categoryId), any(Category.class))).thenReturn(updatedCategory);

        ResponseEntity<String> response = categoryController.updateCategoryById(categoryId, updatedCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category updated successfully", response.getBody());
        verify(categoryService, times(1)).updateCategoryById(eq(categoryId), any(Category.class));
    }
}

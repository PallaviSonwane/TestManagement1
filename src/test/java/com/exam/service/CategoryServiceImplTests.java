package com.exam.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.testmanagement.models.Category;
import com.testmanagement.repository.CategoryRepository;
import com.testmanagement.services.impl.CategoryServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
 class CategoryServiceImplTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void testCreateCategory() {
       
        Category category = new Category(1, "Test Category", "Test Description");

        when(categoryRepository.existsByCategoryName(category.getCategoryName())).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryService.createCategory(category);

        assertEquals(category, createdCategory);
        verify(categoryRepository, times(1)).existsByCategoryName(category.getCategoryName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testGetAllCategoryInfo() {
        
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Description 1"));
        categories.add(new Category(2, "Category 2", "Description 2"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> retrievedCategories = categoryService.getAllCategories();

        assertEquals(categories.size(), retrievedCategories.size());
    }

    @Test
    void testGetCategoryInfo() {
        
        int categoryId = 1;
        Category category = new Category(categoryId, "Test Category", "Test Description");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<Category> retrievedCategory = categoryService.getCategoryById(categoryId);

        assertTrue(retrievedCategory.isPresent());
        assertEquals(categoryId, retrievedCategory.get().getCategoryId());
    }

    @Test
    void testDeleteCategoryByID() {
       
        int categoryId = 1;

        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        boolean deleted = categoryService.deleteCategoryByID(categoryId);

        assertTrue(deleted);
    }

    @Test
    void testUpdateCategoryById() {
        
        int categoryId = 1;
        Category updatedCategory = new Category(categoryId, "Updated Category", "Updated Description");

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.updateCategoryById(categoryId, updatedCategory);

        assertEquals(updatedCategory.getCategoryName(), result.getCategoryName());
        assertEquals(updatedCategory.getCategoryDescription(), result.getCategoryDescription());
    }

    @Test
    void testGetCategory() {
        
        String categoryName = "Test Category";
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Description 1"));
        categories.add(new Category(2, "Category 2", "Description 2"));
        categories.add(new Category(3, "Test Category", "Description"));

        when(categoryRepository.findAll()).thenReturn(categories);

        boolean categoryExists = categoryService.getCategory(categoryName);

        assertTrue(categoryExists);
    }


}

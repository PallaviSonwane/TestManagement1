package com.exam.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.testmanagement.TestManagementApplications;
import com.testmanagement.exceptions.ExceptionManager;
import com.testmanagement.models.Category;
import com.testmanagement.repository.CategoryRepository;
import com.testmanagement.services.impl.CategoryServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestManagementApplications.class)
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
    void testCreateCategory_CategoryAlreadyExists() {
     
        Category existingCategory = new Category(1, "Existing Category", "Existing Description");

        when(categoryRepository.existsByCategoryName(existingCategory.getCategoryName())).thenReturn(true);

       assertThrows(ExceptionManager.class, () -> {
            categoryService.createCategory(existingCategory);
        });
    }

    @Test
    void testGetAllCategories() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Description 1"));
        categories.add(new Category(2, "Category 2", "Description 2"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> retrievedCategories = categoryService.getAllCategories();

        assertEquals(categories.size(), retrievedCategories.size());
    }

    @Test
    void testGetAllCategories_NoCategoriesFound() {
        
        List<Category> emptyList = new ArrayList<>(); 

        when(categoryRepository.findAll()).thenReturn(emptyList);

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            categoryService.getAllCategories();
        });

        assertEquals("Data Not Found", exception.getMessage());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategory() {

        int categoryId = 1;
        Category category = new Category(categoryId, "Test Category", "Test Description");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<Category> retrievedCategory = categoryService.getCategoryById(categoryId);

        assertTrue(retrievedCategory.isPresent());
        assertEquals(categoryId, retrievedCategory.get().getCategoryId());
    }

    @Test
    void testGetCategoryById_CategoryNotFound() {
    
        int categoryId = 1;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            categoryService.getCategoryById(categoryId);
        });

        assertEquals("Category not found in database", exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testDeleteExistingCategory() {

        int categoryId = 1;
        Category categoryToDelete = new Category();
        categoryToDelete.setCategoryId(categoryId);
        categoryToDelete.setCategoryName("category name");
        categoryToDelete.setCategoryDescription("category description");

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategoryByID(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testDeleteNonExistingCategory() {

        int categoryId = 1;

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            categoryService.deleteCategoryByID(categoryId);
        });

        assertEquals("Category Not Found", exception.getMessage());
        verify(categoryRepository, never()).deleteById(1);
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
    void testUpdateCategoryById_CategoryNotFound() {
        
        int categoryId = 1;
        Category categoryToUpdate = new Category(categoryId, "Updated Category", "Updated Description");

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            categoryService.updateCategoryById(categoryId, categoryToUpdate);
        });

        assertEquals("Category Not Found", exception.getMessage());
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).save(categoryToUpdate); 
    }

    @Test
    void testCheckCategory() {

        String categoryName = "Test Category";
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Description 1"));
        categories.add(new Category(2, "Category 2", "Description 2"));
        categories.add(new Category(3, "Test Category", "Description"));

        when(categoryRepository.findAll()).thenReturn(categories);

        boolean categoryExists = categoryService.checkCategory(categoryName);

        assertTrue(categoryExists);
    }
    @Test
    void testCheckCategory_CategoryNotExist() {

        String categoryName = "Test Category";
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Description 1"));
        categories.add(new Category(2, "Category 2", "Description 2"));
        categories.add(new Category(3, "category 3", "Description"));

        when(categoryRepository.findAll()).thenReturn(categories);

        boolean result = categoryService.checkCategory(categoryName);

        assertFalse(result);
        verify(categoryRepository, times(1)).findAll(); }

}

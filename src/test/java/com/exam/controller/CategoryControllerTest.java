package com.exam.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.testmanagement.controllers.CategoryController;
import com.testmanagement.models.Category;
import com.testmanagement.response.SuccessResponse;
import com.testmanagement.services.CategoryService;
import com.testmanagement.services.impl.CategoryServiceImpl;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryService=mock(CategoryServiceImpl.class);
        categoryController=new CategoryController(categoryService);
    }

    @Test
    void testCreateCategory() {
        Category category = new Category();
        category.setCategoryName("Test Category");

        when(categoryService.createCategory(category)).thenReturn(category);
        ResponseEntity<SuccessResponse> responseEntity = categoryController.createCategory(category);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("New Category Created", responseEntity.getBody().getMessage());
        assertEquals(category, responseEntity.getBody().getModuleData());

        verify(categoryService, times(1)).createCategory(category);
    }

    @Test
    void testGetAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Category 1", "Description 1"));
        categoryList.add(new Category(2, "Category 2", "Description 2"));

        when(categoryService.getAllCategories()).thenReturn(categoryList);
        ResponseEntity<SuccessResponse> responseEntity = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("All Categories Retrieved", responseEntity.getBody().getMessage());
        assertEquals(categoryList, responseEntity.getBody().getModuleData());

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testGetCategoryById() {
        int categoryId = 1;
        Category category = new Category(categoryId, "Category 1", "Description 1");
        Optional<Category> optionalCategory = Optional.of(category);

        when(categoryService.getCategoryById(categoryId)).thenReturn(optionalCategory);
        ResponseEntity<SuccessResponse> responseEntity = categoryController.getCategoryById(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Retrieved By Specified Id", responseEntity.getBody().getMessage());

        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    void testGetCategoryById_NotFound() {
        int categoryId = 1;

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.empty());
        ResponseEntity<SuccessResponse> responseEntity = categoryController.getCategoryById(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Retrieved By Specified Id", responseEntity.getBody().getMessage());

        verify(categoryService, times(1)).getCategoryById(categoryId);
    }

    @Test
    void testDeleteCategoryById() {
        int categoryId = 1;
        ResponseEntity<SuccessResponse> responseEntity = categoryController.deleteCategoryById(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Deleted By Specified Id", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());

        verify(categoryService, times(1)).deleteCategoryByID(categoryId);
    }

    @Test
    void testUpdateCategoryById() {
        int categoryId = 1;
        Category updatedCategory = new Category(categoryId, "Updated Category", "Updated Description");

        when(categoryService.updateCategoryById(eq(categoryId), updatedCategory)).thenReturn(updatedCategory);

        ResponseEntity<SuccessResponse> responseEntity = categoryController.updateCategoryById(categoryId, updatedCategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category Updated", responseEntity.getBody().getMessage());
        verify(categoryService, times(1)).updateCategoryById(categoryId, updatedCategory);
    }
}

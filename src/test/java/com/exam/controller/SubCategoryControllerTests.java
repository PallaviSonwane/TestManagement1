package com.exam.controller;

import com.exam.controllers.SubCategoryController;
import com.exam.models.SubCategory;
import com.exam.services.SubCategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubCategoryControllerTests {

    @Mock
    private SubCategoryService subCategoryService;

    @InjectMocks
    private SubCategoryController subCategoryController;

    @Test
    void testCreateSubCategory() {
        
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryID(1);
        subCategory.setSubCategoryName("Test SubCategory");
        subCategory.setSubCategoryDis("Test Description");

        when(subCategoryService.createSubCategory(any(SubCategory.class))).thenReturn(subCategory);

        ResponseEntity<String> response = subCategoryController.createSubCategory(subCategory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Subcategory created successfully", response.getBody());
    }

    @Test
    void testGetAllSubCategories() {
        
        List<SubCategory> subCategories = new ArrayList<>();
        subCategories.add(new SubCategory(1, null, "SubCategory 1", "Description 1"));
        subCategories.add(new SubCategory(2, null, "SubCategory 2", "Description 2"));

        when(subCategoryService.getAllSubCategories()).thenReturn(subCategories);

        ResponseEntity<List<SubCategory>> response = subCategoryController.getAllSubCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetSubCategoryById() {
        int subCategoryId = 1;
        SubCategory subCategory = new SubCategory(subCategoryId, null, "Test SubCategory", "Test Description");

        when(subCategoryService.getSubCategory(subCategoryId)).thenReturn(Optional.of(subCategory));

        ResponseEntity<SubCategory> response = subCategoryController.getSubCategoryById(subCategoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(subCategoryId, response.getBody().getSubCategoryID());
    }

    @Test
    void testUpdateSubCategoryById() {
        int subCategoryId = 1;
        SubCategory updatedSubCategory = new SubCategory(subCategoryId, null, "Updated SubCategory", "Updated Description");

        when(subCategoryService.updateSubCategory(eq(subCategoryId), any(SubCategory.class))).thenReturn(updatedSubCategory);

        ResponseEntity<String> response = subCategoryController.updateSubCategoryById(subCategoryId, updatedSubCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Subcategory updated successfully", response.getBody());
    }

    @Test
    void testDeleteSubCategoryById() {
        int subCategoryId = 1;

        when(subCategoryService.deleteSubCategory(subCategoryId)).thenReturn(true);

        ResponseEntity<String> response = subCategoryController.deleteSubCategoryById(subCategoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Subcategory deleted successfully", response.getBody());
    }
}


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

import com.testmanagement.controllers.SubCategoryController;
import com.testmanagement.models.Category;
import com.testmanagement.models.SubCategory;
import com.testmanagement.response.SuccessResponse;
import com.testmanagement.services.SubCategoryService;

class SubCategoryControllerTest {

    @Mock
    private SubCategoryService subCategoryService;

    @InjectMocks
    private SubCategoryController subCategoryController;

    @BeforeEach
    void setUp() {
        subCategoryService=mock(SubCategoryService.class);
        subCategoryController=new SubCategoryController(subCategoryService);
    }

    @Test
    void testCreateSubCategory() {
        SubCategory subCategory = createMockSubCategory();
        when(subCategoryService.createSubCategory(any(SubCategory.class))).thenReturn(subCategory);
        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.createSubCategory(subCategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("SubCategory Created", responseEntity.getBody().getMessage());
        assertEquals(subCategory, responseEntity.getBody().getModuleData());

        verify(subCategoryService, times(1)).createSubCategory(any(SubCategory.class));
    }

    @Test
    void testGetAllSubCategories() {
        List<SubCategory> subCategoryList = createMockSubCategoryList();
        when(subCategoryService.getAllSubCategories()).thenReturn(subCategoryList);
        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.getAllSubCategories();
       
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("All Subcategory Data Retrieved", responseEntity.getBody().getMessage());
        assertEquals(subCategoryList, responseEntity.getBody().getModuleData());

        verify(subCategoryService, times(1)).getAllSubCategories();
    }

    @Test
    void testGetSubCategoryById() {
        int subCategoryId = 1;
        SubCategory subCategory = createMockSubCategory();
        Optional<SubCategory> optionalSubCategory = Optional.of(subCategory);

        when(subCategoryService.getSubCategory(subCategoryId)).thenReturn(optionalSubCategory);

        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.getSubCategoryById(subCategoryId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Subcategory Data Retrieved", responseEntity.getBody().getMessage());

        verify(subCategoryService, times(1)).getSubCategory(subCategoryId);
    }

    @Test
    void testGetSubCategoryById_NotFound() {
        int subCategoryId = 1;
        when(subCategoryService.getSubCategory(subCategoryId)).thenReturn(Optional.empty());
        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.getSubCategoryById(subCategoryId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Subcategory Data Retrieved", responseEntity.getBody().getMessage());

        verify(subCategoryService, times(1)).getSubCategory(subCategoryId);
    }

    @Test
    void testDeleteSubCategoryById() {
        int subCategoryId = 1;
        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.deleteSubCategoryById(subCategoryId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Subcategory Data deleted", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());

        verify(subCategoryService, times(1)).deleteSubCategory(subCategoryId);
    }

    @Test
    void testUpdateSubCategoryById() {
        int subCategoryId = 1;
        SubCategory subCategory = createMockSubCategory();
        when(subCategoryService.updateSubCategory(eq(subCategoryId), any(SubCategory.class))).thenReturn(subCategory);
        ResponseEntity<SuccessResponse> responseEntity = subCategoryController.updateSubCategoryById(subCategoryId,
                subCategory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Subcategory Data Updated", responseEntity.getBody().getMessage());
        assertEquals(subCategory, responseEntity.getBody().getModuleData());

        verify(subCategoryService, times(1)).updateSubCategory(eq(subCategoryId), any(SubCategory.class));
    }

    private SubCategory createMockSubCategory() {
        Category category = new Category(1, "TestCategory", "TestDescription");
        return new SubCategory(1, category, "TestSubCategory", "TestSubCategoryDescription");
    }

    private List<SubCategory> createMockSubCategoryList() {
        List<SubCategory> subCategoryList = new ArrayList<>();
        subCategoryList.add(createMockSubCategory());
        return subCategoryList;
    }
}

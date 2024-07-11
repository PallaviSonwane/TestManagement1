package com.exam.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.testmanagement.TestManagementApplications;
import com.testmanagement.exceptions.ExceptionManager;
import com.testmanagement.models.Category;
import com.testmanagement.models.SubCategory;
import com.testmanagement.repository.CategoryRepository;
import com.testmanagement.repository.SubCategoryRepository;
import com.testmanagement.services.impl.SubCategoryServiceImp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestManagementApplications.class)
class SubCategoryServiceImplTests {

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SubCategoryServiceImp subCategoryService;

    @Test
    void testCreateSubCategory() {

        Category category = new Category(1, "Test Category", "Test Category Description");
        SubCategory subCategory = new SubCategory(1, category, "Test SubCategory", "Test SubCategory Description");

        when(subCategoryRepository.existsBySubCategoryName(subCategory.getSubCategoryName())).thenReturn(false);
        when(categoryRepository.existsByCategoryName(category.getCategoryName())).thenReturn(true);
        when(subCategoryRepository.save(subCategory)).thenReturn(subCategory);

        SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);

        assertEquals(subCategory, createdSubCategory);
    }

    @Test
    void testCreateSubCategory_DuplicateSubcategory() {

        SubCategory existingSubCategory = new SubCategory();
        existingSubCategory.setSubCategoryName("Existing SubCategory");
        when(subCategoryRepository.existsBySubCategoryName("Existing SubCategory")).thenReturn(true);

        SubCategory newSubCategory = new SubCategory();
        newSubCategory.setSubCategoryName("Existing SubCategory");
        newSubCategory.setCategory(new Category());

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            subCategoryService.createSubCategory(newSubCategory);
        });

        assertEquals("Duplicate Subcategory in database", exception.getMessage());
        verify(subCategoryRepository, never()).save(newSubCategory);
    }

    @Test
    void testGetAllSubCategories() {

        List<SubCategory> subCategories = new ArrayList<>();
        subCategories.add(new SubCategory(1, new Category(), "SubCategory 1", "Description 1"));
        subCategories.add(new SubCategory(2, new Category(), "SubCategory 2", "Description 2"));

        when(subCategoryRepository.findAll()).thenReturn(subCategories);

        List<SubCategory> retrievedSubCategories = subCategoryService.getAllSubCategories();

        assertEquals(subCategories.size(), retrievedSubCategories.size());
    }

    @Test
    void testGetAllSubCategories_NoDataFound() {
        when(subCategoryRepository.findAll()).thenReturn(null);

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            subCategoryService.getAllSubCategories();
        });

        assertEquals("Data Not Present in database", exception.getMessage());
        verify(subCategoryRepository, times(1)).findAll();
    }

    @Test
    void testGetSubCategory() {

        int subCategoryId = 1;
        SubCategory subCategory = new SubCategory(subCategoryId, new Category(), "Test SubCategory",
                "Test Description");

        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        Optional<SubCategory> retrievedSubCategory = subCategoryService.getSubCategory(subCategoryId);

        assertTrue(retrievedSubCategory.isPresent());
        assertEquals(subCategoryId, retrievedSubCategory.get().getSubCategoryId());
    }

    @Test
    void testGetSubCategory_SubCategoryNotFound() {
        int subcategoryId = 100;

        when(subCategoryRepository.findById(subcategoryId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            subCategoryService.getSubCategory(subcategoryId);
        });

        assertEquals("Id not found in database", exception.getMessage());
        verify(subCategoryRepository, times(1)).findById(subcategoryId);
    }

    @Test
    void testDeleteSubCategory_SubCategoryExists() {
        int subCategoryId = 1;
        when(subCategoryRepository.existsById(subCategoryId)).thenReturn(true);

        subCategoryService.deleteSubCategory(subCategoryId);

        verify(subCategoryRepository, times(1)).deleteById(subCategoryId);
    }

    @Test
    void testDeleteSubCategory_SubCategoryNotFound() {
        int subCategoryId = 1;

        when(subCategoryRepository.existsById(subCategoryId)).thenReturn(false);

        Exception ex = assertThrows(ExceptionManager.class, () -> {
            subCategoryService.deleteSubCategory(subCategoryId);
        });

        assertEquals("Id not found in databse", ex.getMessage());
    }

    @Test
    void testUpdateSubCategory() {

        int subCategoryId = 1;
        SubCategory updatedSubCategory = new SubCategory(subCategoryId, new Category(), "Updated SubCategory",
                "Updated Description");

        when(subCategoryRepository.existsById(subCategoryId)).thenReturn(true);
        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(updatedSubCategory);

        SubCategory result = subCategoryService.updateSubCategory(subCategoryId, updatedSubCategory);

        assertEquals(updatedSubCategory.getSubCategoryName(), result.getSubCategoryName());
        assertEquals(updatedSubCategory.getSubCategoryDis(), result.getSubCategoryDis());
    }

    @Test
    void testUpdateSubCategory_SubCategoryNotFound() {
        int subCategoryId = 1;
        SubCategory updatedSubCategory = new SubCategory();
        updatedSubCategory.setSubCategoryId(subCategoryId);

        when(subCategoryRepository.existsById(subCategoryId)).thenReturn(false);

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            subCategoryService.updateSubCategory(subCategoryId, updatedSubCategory);
        });

        assertEquals("Id Not found in database", exception.getMessage());
        verify(subCategoryRepository, never()).save(updatedSubCategory);
    }

    @Test
    void testGetSubCategoryData() {

        String subCategoryName = "Test SubCategory";
        SubCategory subCategory = new SubCategory(1, null, subCategoryName, "Description");

        when(subCategoryRepository.findBySubCategoryName(subCategoryName)).thenReturn(Optional.of(subCategory));

        Optional<SubCategory> retrievedSubCategory = subCategoryService.getSubCategoryData(subCategoryName);

        assertTrue(retrievedSubCategory.isPresent());
        assertEquals(subCategoryName, retrievedSubCategory.get().getSubCategoryName());
    }

    @Test
    void testGetSubCategoryData_SubCategoryNotFound() {
        String subCategoryName = "SubCategory";

        when(subCategoryRepository.findBySubCategoryName(subCategoryName)).thenReturn(Optional.empty());

        Optional<SubCategory> result = subCategoryService.getSubCategoryData(subCategoryName);

        assertFalse(result.isPresent());
        verify(subCategoryRepository, times(1)).findBySubCategoryName(subCategoryName);
    }

}

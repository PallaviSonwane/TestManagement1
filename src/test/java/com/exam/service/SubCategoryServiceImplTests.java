package com.exam.service;

import com.exam.models.Category;
import com.exam.models.SubCategory;
import com.exam.repository.CategoryRepository;
import com.exam.repository.SubCategoryRepository;
import com.exam.services.imlclass.SubCategoryServiceImp;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SubCategoryServiceImplTests {

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SubCategoryServiceImp subCategoryService;

    @Test
    public void testCreateSubCategory() {
       
        Category category = new Category(1, "Test Category", "Test Category Description");
        SubCategory subCategory = new SubCategory(1, category, "Test SubCategory", "Test SubCategory Description");

        when(subCategoryRepository.existsBySubCategoryName(subCategory.getSubCategoryName())).thenReturn(false);
        when(categoryRepository.existsByCategoryName(category.getCategoryName())).thenReturn(true);
        when(subCategoryRepository.save(subCategory)).thenReturn(subCategory);

        SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);

        assertEquals(subCategory, createdSubCategory);
    }

    @Test
    public void testGetAllSubCategories() {
        
        List<SubCategory> subCategories = new ArrayList<>();
        subCategories.add(new SubCategory(1, new Category(), "SubCategory 1", "Description 1"));
        subCategories.add(new SubCategory(2, new Category(), "SubCategory 2", "Description 2"));

        when(subCategoryRepository.findAll()).thenReturn(subCategories);

        List<SubCategory> retrievedSubCategories = subCategoryService.getAllSubCategories();

        assertEquals(subCategories.size(), retrievedSubCategories.size());
    }

    @Test
    public void testGetSubCategory() {
        
        int subCategoryId = 1;
        SubCategory subCategory = new SubCategory(subCategoryId, new Category(), "Test SubCategory", "Test Description");

        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));

        Optional<SubCategory> retrievedSubCategory = subCategoryService.getSubCategory(subCategoryId);

        assertTrue(retrievedSubCategory.isPresent());
        assertEquals(subCategoryId, retrievedSubCategory.get().getSubCategoryID());
    }

    @Test
    public void testDeleteSubCategory() {
      
        int subCategoryId = 1;

        when(subCategoryRepository.existsById(subCategoryId)).thenReturn(true);

        boolean deleted = subCategoryService.deleteSubCategory(subCategoryId);

        assertTrue(deleted);
    }

    @Test
    public void testUpdateSubCategory() {
     
        int subCategoryId = 1;
        SubCategory updatedSubCategory = new SubCategory(subCategoryId, new Category(), "Updated SubCategory", "Updated Description");

        when(subCategoryRepository.existsById(subCategoryId)).thenReturn(true);
        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(updatedSubCategory);

        SubCategory result = subCategoryService.updateSubCategory(subCategoryId, updatedSubCategory);

        assertEquals(updatedSubCategory.getSubCategoryName(), result.getSubCategoryName());
        assertEquals(updatedSubCategory.getSubCategoryDis(), result.getSubCategoryDis());
    }

    @Test
    public void testGetSubCategoryData() {
        
        String subCategoryName = "Test SubCategory";
        SubCategory subCategory = new SubCategory(1, null , subCategoryName, "Description");

        when(subCategoryRepository.findBySubCategoryName(subCategoryName)).thenReturn(Optional.of(subCategory));

        Optional<SubCategory> retrievedSubCategory = subCategoryService.getSubCategoryData(subCategoryName);

        assertTrue(retrievedSubCategory.isPresent());
        assertEquals(subCategoryName, retrievedSubCategory.get().getSubCategoryName());
    }

}

package com.testmanagement.services;

import java.util.Optional;

import com.testmanagement.models.SubCategory;

import java.util.List;

public interface SubCategoryService {
    
    public SubCategory createSubCategory(SubCategory subCategory);

    public List<SubCategory> getAllSubCategories();

    public Optional<SubCategory> getSubCategory(int subCategoryID);

    public boolean deleteSubCategory(int subCategoryID);

    public SubCategory updateSubCategory(int subCategoryID ,SubCategory subCategory);

    public Optional<SubCategory> getSubCategoryData(String name);
    
}

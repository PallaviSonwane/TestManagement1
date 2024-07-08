package com.exam.service;

import com.exam.model.SubCategory;
import java.util.Optional;
import java.util.List;

public interface SubCategoryService {
    
    public SubCategory createSubCategory(SubCategory subCategory);

    public List<SubCategory> getAllSubCategories();

    public Optional<SubCategory> getSubCategory(int subCategoryID);

    public boolean deleteSubCategory(int subCategoryID);

    public SubCategory updateSubCategory(int subCategoryID ,SubCategory subCategory);

    public Optional<SubCategory> getSubCategoryData(String name);
    
}

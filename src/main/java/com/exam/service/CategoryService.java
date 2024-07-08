package com.exam.service;

import com.exam.model.Category;
import java.util.Optional;


import java.util.List;

public interface CategoryService {
   
    public Category createCategory(Category category);

    public List<Category> getAllCategoryInfo();

    public Optional<Category> getCategoryInfo(int categoryId);

    public boolean deleteCategoryByID(int categoryId);

    public Category updateCategoryById(int categoryId ,Category category);

    public boolean getCategory(String name);
}

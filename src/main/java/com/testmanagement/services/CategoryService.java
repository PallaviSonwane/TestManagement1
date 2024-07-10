package com.testmanagement.services;

import java.util.Optional;

import com.testmanagement.models.Category;

import java.util.List;

public interface CategoryService {
   
    public Category createCategory(Category category);

    public List<Category> getAllCategories();

    public Optional<Category> getCategoryById(int categoryId);

    public void deleteCategoryByID(int categoryId);

    public Category updateCategoryById(int categoryId ,Category category);

    public boolean checkCategory(String name);
}

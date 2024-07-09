package com.exam.services;

import java.util.Optional;

import com.exam.models.Category;

import java.util.List;

public interface CategoryService {
   
    public Category createCategory(Category category);

    public List<Category> getAllCategoryInfo();

    public Optional<Category> getCategoryInfo(int categoryId);

    public boolean deleteCategoryByID(int categoryId);

    public Category updateCategoryById(int categoryId ,Category category);

    public boolean getCategory(String name);
}

package com.testmanagement.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.testmanagement.exceptions.ExceptionManager;
import com.testmanagement.models.Category;
import com.testmanagement.repository.CategoryRepository;
import com.testmanagement.services.CategoryService;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        try {
            if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
                throw new ExceptionManager("Category already present in database");
            }
            return categoryRepository.save(category);
        } catch (Exception ex) {
            log.error("some problem in database", ex);
            throw new ExceptionManager("some problem in database, category not added");
        }

    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categoriesList = categoryRepository.findAll();
        if (!categoriesList.isEmpty()) {
            return categoriesList;
        }
        log.error("No categories found,List is empty!");
        throw new ExceptionManager("Data Not Found");
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return category;
        }
        log.warn("Category with ID '{}' not found", categoryId);
        throw new ExceptionManager("Category not found in database");
    }

    @Override
    public void deleteCategoryByID(int categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
            log.info("Category with ID '{}' deleted from database", categoryId);
        }
            log.warn("Category with ID '{}' not found for deletion", categoryId);
            throw new ExceptionManager("Category Not Found");
    }

    @Override
    public Category updateCategoryById(int categoryId, Category category) {
        if (categoryRepository.existsById(categoryId)) {
            log.info("Updating category with ID: {}", categoryId);
            return categoryRepository.save(category);
        }
        log.warn("Category with ID '{}' not found for update", categoryId);
        throw new ExceptionManager("Category Not Found");
    }

    @Override
    public boolean checkCategory(String name) {
        List<Category> categoryList = categoryRepository.findAll();
        for (Category category : categoryList) {
            if (category.getCategoryName().equals(name)) {
                log.info("Category found with name: {}", name);
                return true;
            }
        }
        log.info("Category not found with name: {}", name);
        return false;
    }

}

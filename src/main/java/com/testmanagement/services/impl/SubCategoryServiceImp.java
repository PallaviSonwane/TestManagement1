package com.testmanagement.services.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.testmanagement.exceptions.ExceptionManager;
import com.testmanagement.models.SubCategory;
import com.testmanagement.repository.CategoryRepository;
import com.testmanagement.repository.SubCategoryRepository;
import com.testmanagement.services.SubCategoryService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubCategoryServiceImp implements SubCategoryService {

    private SubCategoryRepository subCategoryRepository;
    private CategoryRepository categoryRepository;

    public SubCategoryServiceImp(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public SubCategory createSubCategory(SubCategory subCategory) {

        if (!subCategoryRepository.existsBySubCategoryName(subCategory.getSubCategoryName())) {
            if (categoryRepository.existsByCategoryName(subCategory.getCategory().getCategoryName())) {
                return subCategoryRepository.save(subCategory);
            }
            log.error("cotegory not found");
            throw new ExceptionManager("category is not present in database");
        }
        log.error("Error creating SubCategory");
        throw new ExceptionManager("Duplicate Subcategory in database");
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        List<SubCategory> subCategoriesList = subCategoryRepository.findAll();
        if (subCategoriesList != null) {
            return subCategoriesList;
        }
        log.error("Error fetching all SubCategories");
        throw new ExceptionManager("Data Not Present in database");
    }

    @Override
    public Optional<SubCategory> getSubCategory(int subCategoryID) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(subCategoryID);
        if (subCategory.isPresent()) {
            return subCategory;
        }
        log.error("Error getting SubCategory by ID: {}");
        throw new ExceptionManager("Id not found in database");

    }

    @Override
    public boolean deleteSubCategory(int subCategoryID) {

        if (subCategoryRepository.existsById(subCategoryID)) {
            subCategoryRepository.deleteById(subCategoryID);
            log.info("SubCategory with ID '{}' deleted from database", subCategoryID);
            return true;
        } else {
            log.error("SubCategory with ID '{}' not found in database", subCategoryID);
            throw new ExceptionManager("Id not found in databse");
        }

    }

    @Override
    public SubCategory updateSubCategory(int subCategoryID, SubCategory subCategory) {
        if (subCategoryRepository.existsById(subCategoryID)) {
            return subCategoryRepository.save(subCategory);
        } 
        log.error("SubCategory with ID '{}' not found in database", subCategoryID);
        throw new ExceptionManager("Id Not found in database");
    }

    @Override
    public Optional<SubCategory> getSubCategoryData(String name) {
            Optional<SubCategory> subCategory = subCategoryRepository.findBySubCategoryName(name);
            if (!subCategory.isPresent()) {
                log.warn("SubCategory with name '{}' not found in database", name);
            }
            return subCategory;
    }
}

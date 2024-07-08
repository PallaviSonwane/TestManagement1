package com.exam.service.imlclass;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.exception.CategoryNotFoundException;
import com.exam.exception.DataNotFoundException;
import com.exam.exception.DuplicateSubCategoryEntry;
import com.exam.model.SubCategory;
import com.exam.repository.CategoryRepository;
import com.exam.repository.SubCategoryRepository;
import com.exam.service.SubCategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubCategoryServiceImp implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public SubCategory createSubCategory(SubCategory subCategory) {
      
            if (!subCategoryRepository.existsBySubCategoryName(subCategory.getSubCategoryName())) {
                if(categoryRepository.existsByCategoryName(subCategory.getCategory().getCategoryName()))
                    return  subCategoryRepository.save(subCategory);
                
                    throw new CategoryNotFoundException("category is not present in database");
            }
           
           throw new DuplicateSubCategoryEntry("Duplicate Subcategory in database");
    }

    @Override
    public List<SubCategory> getAllSubCategories() {
        List<SubCategory> subCategoriesList=subCategoryRepository.findAll();
            if(subCategoriesList!=null)
            return subCategoriesList;

            throw new DataNotFoundException("Data Not Present in database");
    }

    @Override
    public Optional<SubCategory> getSubCategory(int subCategoryID) {
       
            Optional<SubCategory> subCategory = subCategoryRepository.findById(subCategoryID);
            if (subCategory.isPresent()) {
                return subCategory;            
            }

            throw new DataNotFoundException("Id not found in database");
        
    }

    @Override
    public boolean deleteSubCategory(int subCategoryID) {
       
            if (subCategoryRepository.existsById(subCategoryID)) {
                subCategoryRepository.deleteById(subCategoryID);
                log.info("SubCategory with ID '{}' deleted from database", subCategoryID);
                return true;
            } else {
                log.warn("SubCategory with ID '{}' not found in database", subCategoryID);
                throw new DataNotFoundException("Id not found in databse");
            }
        
    }

    @Override
    public SubCategory updateSubCategory(int subCategoryID, SubCategory subCategory) {
       
            if (subCategoryRepository.existsById(subCategoryID)) {
                return subCategoryRepository.save(subCategory);
            } else {
                throw new DataNotFoundException("Id Not found in database");
            }
       
    }

    @Override
    public Optional<SubCategory> getSubCategoryData(String name) {
        try {
            Optional<SubCategory> subCategory = subCategoryRepository.findBySubCategoryName(name);
            if (!subCategory.isPresent()) {
                log.warn("SubCategory with name '{}' not found in database", name);
            }
            return subCategory;
        } catch (Exception ex) {
            log.error("Failed to get SubCategory by name: {}", name, ex);
            return Optional.empty();
        }
    }
}

package com.exam.service.imlclass;

import com.exam.exception.DataNotFoundException;
import com.exam.exception.DuplicateCategoryEntry;
import com.exam.model.Category;
import com.exam.repository.CategoryRepository;
import com.exam.service.CategoryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        
            if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
                throw new DuplicateCategoryEntry("Category already present in database");
            }
           return  categoryRepository.save(category);
       
    }

    @Override
    public List<Category> getAllCategoryInfo() {
       List<Category> categoriesList=categoryRepository.findAll();
       if(!categoriesList.isEmpty())
       return categoriesList;

       throw new DataNotFoundException("Data Not Found");
    }

    @Override
    public Optional<Category> getCategoryInfo(int categoryId) {
        Optional<Category> categoryId1=categoryRepository.findById(categoryId);
        if (categoryId1.isPresent()) 
        return categoryRepository.findById(categoryId);

        throw new DataNotFoundException("Category not found in database");
    }

    @Override
    public boolean deleteCategoryByID(int categoryId) {
            if (categoryRepository.existsById(categoryId)) {
                categoryRepository.deleteById(categoryId);
                log.info("Category with ID '{}' deleted from database", categoryId);
                return true;
            }else{
                throw new DataNotFoundException("Category Not Found");
            }
            
    }

    @Override
    public Category updateCategoryById(int categoryId, Category category) {

            if (categoryRepository.existsById(categoryId)) 
                return  categoryRepository.save(category);;
            
            throw new DataNotFoundException("Category Not Found");
        
    }

    @Override
    public boolean getCategory(String name) {
        List<Category> categoryList = categoryRepository.findAll();

        for (Category category : categoryList) {
            if (category.getCategoryName().equals(name)) {
                return true;
            }
        }

        return false;
    }

}

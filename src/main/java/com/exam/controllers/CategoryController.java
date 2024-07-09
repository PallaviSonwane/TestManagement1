package com.exam.controllers;


import com.exam.exceptions.CategoryNotFoundException;
import com.exam.exceptions.DataNotFoundException;
import com.exam.exceptions.DuplicateCategoryEntry;
import com.exam.models.Category;
import com.exam.services.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        if (created != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully");
        } 

        throw new DuplicateCategoryEntry("category already present in databse");
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategoryInfo();
        if(categories!=null)
        return ResponseEntity.ok(categories);

        throw new DataNotFoundException("List is empty");
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId") int categoryId) {
        Optional<Category> category = categoryService.getCategoryInfo(categoryId);
        return category.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable("categoryId") int categoryId) {
        boolean deleted = categoryService.deleteCategoryByID(categoryId);
        if (deleted) {
            return ResponseEntity.ok("Category deleted successfully");
        } 
        throw new CategoryNotFoundException("category not present in database");
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategoryById(@PathVariable("categoryId") int categoryId,
                                                     @RequestBody Category category) {
        Category updated = categoryService.updateCategoryById(categoryId, category);
        if (updated!=null) {
            return ResponseEntity.ok("Category updated successfully");
        } 

        throw new CategoryNotFoundException("category not found in databse");
    }

}

package com.testmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.testmanagement.models.Category;
import com.testmanagement.response.SuccessResponse;
import com.testmanagement.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createCategory(@RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        log.info("Category created successfully",created);
        SuccessResponse successResponse = new SuccessResponse("New Category Created", 200, created);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        SuccessResponse successResponse = new SuccessResponse("All Categories Retrieved", 200, categories);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse> getCategoryById(@PathVariable("categoryId") int categoryId) {
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        SuccessResponse successResponse = new SuccessResponse("Category Retrieved By Specified Id", 200,
                category);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse> deleteCategoryById(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategoryByID(categoryId);
        log.info("Category deleted successfully");
        SuccessResponse successResponse = new SuccessResponse("Category Deleted By Specified Id", 200, null);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse> updateCategoryById(@PathVariable("categoryId") int categoryId,
            @RequestBody Category category) {
        Category categoryUpdated = categoryService.updateCategoryById(categoryId, category);
        log.info("Category updated successfully",categoryUpdated);
        SuccessResponse successResponse = new SuccessResponse("Category Updated", 200, categoryUpdated);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}

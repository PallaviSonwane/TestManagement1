package com.exam.controllers;


import com.exam.exceptions.DataNotFoundException;
import com.exam.exceptions.DuplicateCategoryEntry;
import com.exam.models.SubCategory;
import com.exam.services.SubCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subCategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<String> createSubCategory(@RequestBody SubCategory subCategory) {
        SubCategory created = subCategoryService.createSubCategory(subCategory);
        if (created!=null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Subcategory created successfully");
        } 

        throw new DuplicateCategoryEntry("category already present in database");
    }

    @GetMapping
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
        if(subCategories!=null)
        return ResponseEntity.ok(subCategories);

        throw new DataNotFoundException("subcategory list is null");
    }

    @GetMapping("/{subCategoryID}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable("subCategoryID") int subCategoryID) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategory(subCategoryID);
        return subCategory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{subCategoryID}")
    public ResponseEntity<String> deleteSubCategoryById(@PathVariable("subCategoryID") int subCategoryID) {
        boolean deleted = subCategoryService.deleteSubCategory(subCategoryID);
        if (deleted) {
            return ResponseEntity.ok("Subcategory deleted successfully");
        } 

        throw new DataNotFoundException("subcategory not found in databse");
    }

    @PutMapping("/{subCategoryID}")
    public ResponseEntity<String> updateSubCategoryById(@PathVariable("subCategoryID") int subCategoryID,
                                                        @RequestBody SubCategory subCategory) {
        SubCategory updated = subCategoryService.updateSubCategory(subCategoryID, subCategory);
        if (updated!=null) {
            return ResponseEntity.ok("Subcategory updated successfully");
        } 

        throw new DataNotFoundException("subcategory not found in datbase");
    }
}

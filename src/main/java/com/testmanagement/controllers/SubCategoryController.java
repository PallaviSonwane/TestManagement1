package com.testmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.testmanagement.models.SubCategory;
import com.testmanagement.response.SuccessResponse;
import com.testmanagement.services.SubCategoryService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/subCategory")
public class SubCategoryController {

    private SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createSubCategory(@RequestBody SubCategory subCategory) {
        SubCategory createdSubcategory = subCategoryService.createSubCategory(subCategory);
        log.info("Subcategory created successfully",createdSubcategory);
        SuccessResponse successResponse = new SuccessResponse("SubCategory Created", 200, createdSubcategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
        SuccessResponse successResponse = new SuccessResponse("All Subcategory Data Retrieved", 200, subCategories);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{subCategoryID}")
    public ResponseEntity<SuccessResponse> getSubCategoryById(@PathVariable("subCategoryID") int subCategoryID) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategory(subCategoryID);
        SuccessResponse successResponse = new SuccessResponse("Subcategory Data Retrieved", 200, subCategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);

    }

    @DeleteMapping("/{subCategoryID}")
    public ResponseEntity<SuccessResponse> deleteSubCategoryById(@PathVariable("subCategoryID") int subCategoryID) {
        subCategoryService.deleteSubCategory(subCategoryID);
        log.info("Subcategory deleted successfully");
        SuccessResponse successResponse = new SuccessResponse("Subcategory Data deleted", 200, null);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{subCategoryID}")
    public ResponseEntity<SuccessResponse> updateSubCategoryById(@PathVariable("subCategoryID") int subCategoryID,
            @RequestBody SubCategory subCategory) {
        SubCategory updatedSubcategory = subCategoryService.updateSubCategory(subCategoryID, subCategory);
        log.info("Subcategory updated successfully",updatedSubcategory);
        SuccessResponse successResponse = new SuccessResponse("Subcategory Data Updated", 200, updatedSubcategory);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}

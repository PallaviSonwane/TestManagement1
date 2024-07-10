package com.testmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testmanagement.models.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory , Integer> {

    boolean existsBySubCategoryName(String subCategoryName);

    Optional<SubCategory> findBySubCategoryName(String name);

}

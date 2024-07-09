package com.exam.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.models.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory , Integer> {

    boolean existsBySubCategoryName(String subCategoryName);

    Optional<SubCategory> findBySubCategoryName(String name);

}

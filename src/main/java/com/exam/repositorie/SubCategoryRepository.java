package com.exam.repositorie;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory , Integer> {

    boolean existsBySubCategoryName(String subCategoryName);

    Optional<SubCategory> findBySubCategoryName(String name);

}

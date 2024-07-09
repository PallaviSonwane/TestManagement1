package com.exam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {

    boolean existsByCategoryName(String categoryName);

}

package com.testmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testmanagement.models.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam , Integer> {

    boolean existsByQuestion(String question);
    
}

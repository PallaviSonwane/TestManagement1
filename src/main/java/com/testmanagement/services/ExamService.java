package com.testmanagement.services;


import java.io.IOException;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.testmanagement.models.Exam;


public interface ExamService {
    
    public void processExcel(MultipartFile file1) throws IOException;

    public Exam createMultipleChoiceQuestion(Exam exam);

    public List<Exam> viewAllQuestions();

    public Optional<Exam> viewQuestionById(Integer questionId);

    public ResponseEntity<String> deleteQuestionById(Integer questionId);

    public Exam updateQuestion(Integer questionId, Exam exam);

}

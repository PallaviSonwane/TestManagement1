package com.exam.services;


import java.io.IOException;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.exam.models.Exam;


public interface ExamService {
    
    public void processExcel(MultipartFile file1) throws IOException;

    public Exam addQuestion(Exam exam);

    public List<Exam> viewAllQuestions();

    public Optional<Exam> viewQuestionById(int question_id);

    public ResponseEntity<?> deleteQuestionById(int question_id);

    public Exam updateQuestion(int question_id, Exam exam);

}

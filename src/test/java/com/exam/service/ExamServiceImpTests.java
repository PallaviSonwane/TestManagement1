package com.exam.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.testmanagement.models.Exam;
import com.testmanagement.models.SubCategory;
import com.testmanagement.repository.ExamRepository;
import com.testmanagement.repository.SubCategoryRepository;
import com.testmanagement.services.CategoryService;
import com.testmanagement.services.SubCategoryService;
import com.testmanagement.services.impl.ExamServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExamServiceImpTests {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private SubCategoryService subCategoryService;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @InjectMocks
    private ExamServiceImpl examService;

    @Test
    void testAddQuestion() {

        SubCategory subCategory = new SubCategory(1, null, "SubCategory Name", "SubCategory Description");
        Exam exam = new Exam(1, subCategory, "Question Text", "Option 1", "Option 2", "Option 3", "Option 4", "Answer",
                "1", "0");

        when(examRepository.existsByQuestion(exam.getQuestion())).thenReturn(false);

        when(subCategoryRepository.existsById(exam.getSubCategory().getSubCategoryID())).thenReturn(true);

        when(examRepository.save(exam)).thenReturn(exam);

        Exam savedExam = examService.addQuestion(exam);

        assertNotNull(savedExam);
        assertEquals(exam.getQuestion(), savedExam.getQuestion());
        assertEquals(exam.getSubCategory(), savedExam.getSubCategory());

        verify(examRepository, times(1)).existsByQuestion(exam.getQuestion());
        verify(subCategoryRepository, times(1)).existsById(exam.getSubCategory().getSubCategoryID());
        verify(examRepository, times(1)).save(exam);
    }

    @Test
    void testViewAllQuestions() {

        List<Exam> examList = new ArrayList<>();
        examList.add(
                new Exam(1, null, "Question 1", "Option 1", "Option 2", "Option 3", "Option 4", "Answer", "1", "0"));
        examList.add(
                new Exam(2, null, "Question 2", "Option 1", "Option 2", "Option 3", "Option 4", "Answer", "1", "0"));

        when(examRepository.findAll()).thenReturn(examList);

        List<Exam> result = examService.viewAllQuestions();

        assertEquals(examList.size(), result.size());
        assertEquals(examList.get(0).getQuestion(), result.get(0).getQuestion());
        assertEquals(examList.get(1).getQuestion(), result.get(1).getQuestion());

        verify(examRepository, times(1)).findAll();
    }

    @Test
    void testViewQuestionById() {

        int questionId = 1;
        Exam exam = new Exam(questionId, null, "Question Text", "Option 1", "Option 2", "Option 3", "Option 4",
                "Answer", "1", "0");

        when(examRepository.existsById(questionId)).thenReturn(true);
        when(examRepository.findById(questionId)).thenReturn(Optional.of(exam));

        Optional<Exam> result = examService.viewQuestionById(questionId);

        assertTrue(result.isPresent());
        assertEquals(exam.getQuestion(), result.get().getQuestion());

        verify(examRepository, times(1)).existsById(questionId);
        verify(examRepository, times(1)).findById(questionId);
    }

    @Test
    void testDeleteQuestionById() {

        int questionId = 1;

        when(examRepository.existsById(questionId)).thenReturn(true);

        ResponseEntity<?> result = examService.deleteQuestionById(questionId);

        verify(examRepository, times(1)).existsById(questionId);
        verify(examRepository, times(1)).deleteById(questionId);
        assertEquals(ResponseEntity.ok().build(), result);
    }

    @Test
    void testUpdateQuestion() {

        int questionId = 1;
        SubCategory subCategory = new SubCategory(1, null, "SubCategory Name", "SubCategory Description");
        Exam updatedExam = new Exam(questionId, subCategory, "Updated Question", "Updated Option 1", "Updated Option 2",
                "Updated Option 3", "Updated Option 4", "Updated Answer", "2", "0");

        when(examRepository.existsById(questionId)).thenReturn(true);
        when(subCategoryRepository.existsById(updatedExam.getSubCategory().getSubCategoryID())).thenReturn(true);
        when(examRepository.save(updatedExam)).thenReturn(updatedExam);

        Exam result = examService.updateQuestion(questionId, updatedExam);

        assertEquals(updatedExam.getQuestion(), result.getQuestion());
        assertEquals(updatedExam.getOption1(), result.getOption1());

        verify(examRepository, times(1)).existsById(questionId);
        verify(subCategoryRepository, times(1)).existsById(updatedExam.getSubCategory().getSubCategoryID());
        verify(examRepository, times(1)).save(updatedExam);
    }

}

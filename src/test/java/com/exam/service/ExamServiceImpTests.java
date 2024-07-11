package com.exam.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.testmanagement.TestManagementApplications;
import com.testmanagement.exceptions.ExceptionManager;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestManagementApplications.class)
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

        when(subCategoryRepository.existsById(exam.getSubCategory().getSubCategoryId())).thenReturn(true);

        when(examRepository.save(exam)).thenReturn(exam);

        Exam savedExam = examService.createMultipleChoiceQuestion(exam);

        assertNotNull(savedExam);
        assertEquals(exam.getQuestion(), savedExam.getQuestion());
        assertEquals(exam.getSubCategory(), savedExam.getSubCategory());

        verify(examRepository, times(1)).existsByQuestion(exam.getQuestion());
        verify(subCategoryRepository, times(1)).existsById(exam.getSubCategory().getSubCategoryId());
        verify(examRepository, times(1)).save(exam);
    }

    @Test
    void testCreateMultipleChoiceQuestion_SubCategoryNotFound() {
        when(subCategoryRepository.existsById(1)).thenReturn(false);
        assertThrows(ExceptionManager.class, () -> {
            Exam exam = new Exam();
            exam.setSubCategory(new SubCategory(1, null, "Non-existent Subcategory", "Description"));
            exam.setQuestion("Sample Question");
            exam.setOption1("Option 1");
            exam.setOption2("Option 2");
            exam.setOption3("Option 3");
            exam.setOption4("Option 4");
            exam.setAns("Option 1");
            exam.setPositiveMark("3");
            exam.setNegativeMark("-1");

            examService.createMultipleChoiceQuestion(exam);
        });
        verify(subCategoryRepository, times(1)).existsById(1);
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
    void testViewAllQuestions_ListIsNull() {
        when(examRepository.findAll()).thenReturn(null);

        assertThrows(ExceptionManager.class, () -> {
            examService.viewAllQuestions();
        });
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
    void testViewQuestionById_QuestionIdNotFound() {
        int questionId = 1;
        when(examRepository.existsById(questionId)).thenReturn(false);

        Exception exception = assertThrows(ExceptionManager.class, () -> {
            examService.viewQuestionById(questionId);
        });

        assertEquals("this Question id not present in database", exception.getMessage());
        verify(examRepository, times(1)).existsById(questionId);
    }

    @Test
    void testDeleteQuestionById() {

        int questionId = 1;

        when(examRepository.existsById(questionId)).thenReturn(true);

        examService.deleteQuestionById(questionId);

        verify(examRepository, times(1)).existsById(questionId);
        verify(examRepository, times(1)).deleteById(questionId);
    }

    @Test
    void testDeleteQuestionById_QuestionIdNotFound() {
        int questionId = 1;
        when(examRepository.existsById(questionId)).thenReturn(false);

        assertThrows(ExceptionManager.class, () -> {
            examService.deleteQuestionById(questionId);
        });
        verify(examRepository, times(1)).existsById(questionId);
    }

    @Test
    void testUpdateQuestion() {

        int questionId = 1;
        SubCategory subCategory = new SubCategory(1, null, "SubCategory Name", "SubCategory Description");
        Exam updatedExam = new Exam(questionId, subCategory, "Updated Question", "Updated Option 1", "Updated Option 2",
                "Updated Option 3", "Updated Option 4", "Updated Answer", "2", "0");

        when(examRepository.existsById(questionId)).thenReturn(true);
        when(subCategoryRepository.existsById(updatedExam.getSubCategory().getSubCategoryId())).thenReturn(true);
        when(examRepository.save(updatedExam)).thenReturn(updatedExam);

        Exam result = examService.updateQuestion(questionId, updatedExam);

        assertEquals(updatedExam.getQuestion(), result.getQuestion());
        assertEquals(updatedExam.getOption1(), result.getOption1());

        verify(examRepository, times(1)).existsById(questionId);
        verify(subCategoryRepository, times(1)).existsById(updatedExam.getSubCategory().getSubCategoryId());
        verify(examRepository, times(1)).save(updatedExam);
    }

    @Test
    void testUpdateQuestion_SubCategoryNotPresent() {
        int questionId = 1;
        when(examRepository.existsById(questionId)).thenReturn(true);
        when(subCategoryRepository.existsById(anyInt())).thenReturn(false);

        Exam updatedExam = new Exam();
        updatedExam.setQuestionId(1);
        updatedExam.setSubCategory(new SubCategory(1, null, "Non-existent Subcategory", "Description"));
        updatedExam.setQuestion("Sample Question");
        updatedExam.setOption1("Option 1");
        updatedExam.setOption2("Option 2");
        updatedExam.setOption3("Option 3");
        updatedExam.setOption4("Option 4");
        updatedExam.setAns("Option 1");
        updatedExam.setPositiveMark("3");
        updatedExam.setNegativeMark("-1");
        updatedExam.setQuestionId(questionId);

         assertThrows(ExceptionManager.class, () -> {
            examService.updateQuestion(questionId, updatedExam);
        });

        verify(examRepository, times(1)).existsById(questionId);
        verify(subCategoryRepository, times(1)).existsById(1);
    }

}

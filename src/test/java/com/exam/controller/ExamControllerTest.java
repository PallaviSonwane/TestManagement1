package com.exam.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.testmanagement.controllers.ExamController;
import com.testmanagement.models.Exam;
import com.testmanagement.response.SuccessResponse;
import com.testmanagement.services.ExamService;

class ExamControllerTest {

    @Mock
    private ExamService examService;

    @InjectMocks
    private ExamController examController;

    @BeforeEach
    void setUp() {
        examService=mock(ExamService.class);
        examController=new ExamController(examService);
    }

    @Test
    void testCreateMultipleChoiceQuestion() {
        Exam exam =new Exam(1, null, "Question", "op1", "op2", "op3", "op4", "ans", "3", "-1");
        when(examService.createMultipleChoiceQuestion(exam)).thenReturn(exam);
        ResponseEntity<SuccessResponse> responseEntity = examController.createMultipleChoiceQuestion(exam);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("New Question Saved", responseEntity.getBody().getMessage());
        assertEquals(exam, responseEntity.getBody().getModuleData());

        verify(examService, times(1)).createMultipleChoiceQuestion(any(Exam.class));
    }

    @Test
    void testGetAllQuestions() {
        List<Exam> examList = new ArrayList<>();
        examList.add(new Exam(1, null, "Question", "op1", "op2", "op3", "op4", "ans", "3", "-1"));
        when(examService.viewAllQuestions()).thenReturn(examList);
        ResponseEntity<SuccessResponse> responseEntity = examController.getAllQuestions();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("All Data Retrieved", responseEntity.getBody().getMessage());
        assertEquals(examList, responseEntity.getBody().getModuleData());

        verify(examService, times(1)).viewAllQuestions();
    }

    @Test
    void testGetQuestionById() {
        int questionId = 1;
        Exam exam = new Exam(1, null, "Question", "op1", "op2", "op3", "op4", "ans", "3", "-1");
        Optional<Exam> optionalExam = Optional.of(exam);
        when(examService.viewQuestionById(questionId)).thenReturn(optionalExam);
        ResponseEntity<SuccessResponse> responseEntity = examController.getQuestionById(questionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question Data get by id", responseEntity.getBody().getMessage());

        verify(examService, times(1)).viewQuestionById(questionId);
    }

    @Test
    void testGetQuestionById_NotFound() {
        int questionId = 1;
        when(examService.viewQuestionById(questionId)).thenReturn(Optional.empty());
        ResponseEntity<SuccessResponse> responseEntity = examController.getQuestionById(questionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question Data get by id", responseEntity.getBody().getMessage());

        verify(examService, times(1)).viewQuestionById(questionId);
    }

    @Test
    void testDeleteQuestionById() {
        int questionId = 1;
        ResponseEntity<SuccessResponse> responseEntity = examController.deleteQuestionById(questionId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question Data Deleted", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getModuleData());

        verify(examService, times(1)).deleteQuestionById(questionId);
    }

    @Test
    void testUpdateQuestionById() {
        int questionId = 1;
        Exam exam = new Exam(1, null, "Question", "op1", "op2", "op3", "op4", "ans", "3", "-1");
        when(examService.updateQuestion(eq(questionId), any(Exam.class))).thenReturn(exam);
        ResponseEntity<SuccessResponse> responseEntity = examController.updateQuestionById(questionId, exam);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question Data Updated", responseEntity.getBody().getMessage());
        assertEquals(exam, responseEntity.getBody().getModuleData());

        verify(examService, times(1)).updateQuestion(eq(questionId), any(Exam.class));
    }

    @Test
    void testUploadData() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        ResponseEntity<SuccessResponse> responseEntity = examController.uploadData(mockFile);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Question Data Uploaded", responseEntity.getBody().getMessage());

        verify(examService, times(1)).processExcel(mockFile);
    }
}

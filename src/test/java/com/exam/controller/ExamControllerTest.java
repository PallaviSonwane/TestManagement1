package com.exam.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.exam.controllers.ExamController;
import com.exam.models.Exam;
import com.exam.services.ExamService;

@SpringBootTest
public class ExamControllerTest {

    @Mock
    private ExamService examService;

    @InjectMocks
    private ExamController examController;

    @Test
    public void testCreateQuestion(){
        Exam examModel=new Exam(1,null,"Question 1","op 1","op 2","op 3","op 4","ans 1","3","-2");

        when(examService.addQuestion(examModel)).thenReturn(examModel);
        ResponseEntity<String> added=examController.addQuestion(examModel);

        assertEquals(HttpStatus.CREATED, added.getStatusCode());
        assertEquals("Question added successfully", added.getBody());   
    }

    @Test
    public void testGetAllQuestion(){
        List<Exam> questionList=new ArrayList<>();
        questionList.add(new Exam(1,null,"Question 1","op 1","op 2","op 3","op 4","ans 1","3","-1"));
        questionList.add(new Exam(2,null,"Question 2","op 1","op 2","op 3","op 4","ans 2","2","-2"));
        
        when(examService.viewAllQuestions()).thenReturn(questionList);
        ResponseEntity<List<Exam>> questions=examController.getAllQuestions();

        assertEquals(HttpStatus.OK, questions.getStatusCode());
        assertEquals(questionList, questions.getBody());
    }

    @Test
    public void testGetQuestionById(){
        int questionId=1;
        Exam qExam=new Exam(1,null,"Question 1","op 1","op 2","op 3","op 4","ans 1","3","-1");

        when(examService.viewQuestionById(questionId)).thenReturn(Optional.of(qExam));
        ResponseEntity<Exam> response=examController.getQuestionById(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(questionId, response.getBody().getQuestion_id());
    }

    @Test
    public void testDeleteQuestionById() {
       
        int questionId = 1;

        when(examService.deleteQuestionById(questionId)).thenReturn(ResponseEntity.ok().build());

        ResponseEntity<String> response = examController.deleteQuestionById(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Question deleted successfully", response.getBody());
    }

      @Test
    public void testUpdateQuestionById() {
        
        int questionId = 1;
        Exam updatedExam = new Exam(questionId, null, "What is Java?", "A programming language", "A type of coffee", "A framework", "Abc","ans", "3", "-2");

        when(examService.updateQuestion(eq(questionId), any(Exam.class))).thenReturn(updatedExam);

        ResponseEntity<String> response = examController.updateQuestionById(questionId, updatedExam);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Question updated successfully", response.getBody());
    }
}

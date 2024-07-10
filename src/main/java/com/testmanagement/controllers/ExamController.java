package com.testmanagement.controllers;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.testmanagement.models.Exam;
import com.testmanagement.response.SuccessResponse;
import com.testmanagement.services.ExamService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/exam")
public class ExamController {

    private ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createMultipleChoiceQuestion(@RequestBody Exam exam) {
        Exam examData = examService.createMultipleChoiceQuestion(exam);
        SuccessResponse successResponse = new SuccessResponse("New Question Saved", 201, examData);
        log.info("Successfully created new MCQ question: {}", examData);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllQuestions() {
        List<Exam> questions = examService.viewAllQuestions();
        SuccessResponse successResponse = new SuccessResponse("All Data Retrieved", 200, questions);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{question_id}")
    public ResponseEntity<SuccessResponse> getQuestionById(@PathVariable("question_id") Integer questionId) {
        Optional<Exam> question = examService.viewQuestionById(questionId);
        SuccessResponse sResponse = new SuccessResponse("Question Data get by id", 200, question);
        return new ResponseEntity<>(sResponse, HttpStatus.OK);

    }

    @DeleteMapping("/{question_id}")
    public ResponseEntity<SuccessResponse> deleteQuestionById(@PathVariable("question_id") Integer questionId) {
        examService.deleteQuestionById(questionId);
        SuccessResponse successResponse = new SuccessResponse("Question Data Deleted", 200, null);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{question_id}")
    public ResponseEntity<SuccessResponse> updateQuestionById(@PathVariable("question_id") Integer questionId,
            @RequestBody Exam exam) {
        Exam updated = examService.updateQuestion(questionId, exam);
        SuccessResponse sResponse = new SuccessResponse("Question Data Updated", 200, updated);
        return new ResponseEntity<>(sResponse, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<SuccessResponse> uploadData(@RequestParam("file") MultipartFile file) throws IOException {
        examService.processExcel(file);
        SuccessResponse successResponse = new SuccessResponse("Question Data Uploaded", 200, null);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}

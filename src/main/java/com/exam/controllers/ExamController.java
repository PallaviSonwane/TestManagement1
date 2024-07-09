package com.exam.controllers;

import com.exam.exceptions.DataNotFoundException;
import com.exam.exceptions.DuplicatedDataException;
import com.exam.models.Exam;
import com.exam.services.ExamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exam")
public class ExamController {

    @Autowired
    private ExamService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) {
        try {
            service.processExcel(file);
            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process file: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> addQuestion(@RequestBody Exam exam) {
        Exam saved = service.addQuestion(exam);
        if (saved!=null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Question added successfully");
        } 

        throw new DuplicatedDataException("question already present in databse");
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getAllQuestions() {
        List<Exam> questions = service.viewAllQuestions();
        if(questions!=null){
            return ResponseEntity.ok(questions);
        }

        throw new DataNotFoundException("Question List is null");
    }

    @GetMapping("/{question_id}")
    public ResponseEntity<Exam> getQuestionById(@PathVariable("question_id") int question_id) {
        Optional<Exam> question = service.viewQuestionById(question_id);
        return question.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{question_id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable("question_id") int question_id) {
        ResponseEntity<?> deleted = service.deleteQuestionById(question_id);
        if (deleted != null) {
            return ResponseEntity.ok("Question deleted successfully");
        } 

        throw new DataNotFoundException("question id not found in databse");
    }    

    @PutMapping("/{question_id}")
    public ResponseEntity<String> updateQuestionById(@PathVariable("question_id") int question_id,
                                                     @RequestBody Exam exam) {
        Exam updated = service.updateQuestion(question_id, exam);
        if (updated!=null) {
            return ResponseEntity.ok("Question updated successfully");
        } 

        throw new DataNotFoundException("Question id not found in databse");
    }
}

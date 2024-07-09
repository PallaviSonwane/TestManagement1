package com.exam.services.imlclass;


import com.exam.exceptions.DataNotFoundException;
import com.exam.exceptions.DuplicatedDataException;
import com.exam.exceptions.SubcategoryNotFoundException;
import com.exam.models.Exam;
import com.exam.models.SubCategory;
import com.exam.repository.ExamRepository;
import com.exam.repository.SubCategoryRepository;
import com.exam.services.CategoryService;
import com.exam.services.ExamService;
import com.exam.services.SubCategoryService;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExamServiceImp implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public Exam addQuestion(Exam exam) {
        
        if(!examRepository.existsByQuestion(exam.getQuestion())){
            if(subCategoryRepository.existsById(exam.getSubCategory().getSubCategoryID()))
            return examRepository.save(exam);

            throw new SubcategoryNotFoundException("Subcategory not found");
        }
            log.error("Data already present in given id");
            throw new DuplicatedDataException("this question already present in database");
    }

    @Override
    public List<Exam> viewAllQuestions() {
        List<Exam> examList = examRepository.findAll();
        if (examList != null) 
            return examList;

            log.error("Failed to fetch questions: List is null");
            throw new DataNotFoundException("List is Null ");
        
    }

    @Override
    public Optional<Exam> viewQuestionById(int questionId) {
        if (examRepository.existsById(questionId)) 
            return examRepository.findById(questionId);
       
            throw new DataNotFoundException("this Question id not present in database");
    }

    @Override
    public ResponseEntity<?> deleteQuestionById(int questionId) {
        if (examRepository.existsById(questionId)) {
            examRepository.deleteById(questionId);
            log.info("Question deleted successfully: {}", questionId);
            return ResponseEntity.ok().build();
        }

        throw new DataNotFoundException("Question id not found ");

    }

    @Override
    public Exam updateQuestion(int questionId, Exam updatedExam) {
        if (examRepository.existsById(questionId)) {
           if(subCategoryRepository.existsById(updatedExam.getSubCategory().getSubCategoryID())) 
            return examRepository.save(updatedExam);
           
            throw new SubcategoryNotFoundException("subcategory id not found ");     
        } 
        throw new DataNotFoundException("data not found");
    }

    public boolean isQuestionUnique(String question) {
            List<Exam> examList = examRepository.findAll();
            if (examList == null) {
                log.error("Failed to fetch questions: List is null");
                return false;
            }
            for (Exam exam : examList) {
                if (exam.getQuestion().equalsIgnoreCase(question)) {
                    return false;
                }
            }
            return true;
    }

    @Override
    public void processExcel(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
             Workbook workbook = new HSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                String categoryName = row.getCell(1).getStringCellValue();
                String subCategoryName = row.getCell(2).getStringCellValue();
                String question = row.getCell(3).getStringCellValue();
                String option1 = row.getCell(4).getStringCellValue();
                String option2 = row.getCell(5).getStringCellValue();
                String option3 = row.getCell(6).getStringCellValue();
                String option4 = row.getCell(7).getStringCellValue();
                String ans = row.getCell(8).getStringCellValue();
                String positiveMark = String.valueOf(row.getCell(9).getNumericCellValue());
                String negativeMark = String.valueOf(row.getCell(10).getNumericCellValue());

                if (categoryService.getCategory(categoryName) && isQuestionUnique(question)) {
                    Optional<SubCategory> subCategoryOptional = subCategoryService.getSubCategoryData(subCategoryName);
                    subCategoryOptional.ifPresent(subCategory -> {
                        Exam examEntity = new Exam();
                        examEntity.setSubCategory(subCategory);
                        examEntity.setQuestion(question);
                        examEntity.setOption1(option1);
                        examEntity.setOption2(option2);
                        examEntity.setOption3(option3);
                        examEntity.setOption4(option4);
                        examEntity.setAns(ans);
                        examEntity.setPositiveMark(positiveMark);
                        examEntity.setNegativeMark(negativeMark);

                        Exam savedExam = examRepository.save(examEntity);
                        if (savedExam != null) {
                            log.info("Exam data saved successfully: {}", savedExam);
                        } else {
                            log.error("Failed to save Exam data");
                        }
                    });
                } else {
                    if (!categoryService.getCategory(categoryName)) {
                        log.error("Category '{}' not found in database", categoryName);
                    }
                    if (!isQuestionUnique(question)) {
                        log.error("Question '{}' already present in database", question);
                    }
                    if (subCategoryService.getSubCategoryData(subCategoryName).isEmpty()) {
                        log.error("SubCategory '{}' not found in database", subCategoryName);
                    }
                }
            }
            workbook.close();;
        } catch (IOException e) {
            log.error("Error processing Excel file: {}", e.getMessage());
        }
    }
}

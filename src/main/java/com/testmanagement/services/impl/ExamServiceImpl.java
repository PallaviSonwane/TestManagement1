package com.testmanagement.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.testmanagement.exceptions.ExceptionManager;
import com.testmanagement.models.Exam;
import com.testmanagement.models.SubCategory;
import com.testmanagement.repository.ExamRepository;
import com.testmanagement.repository.SubCategoryRepository;
import com.testmanagement.services.CategoryService;
import com.testmanagement.services.ExamService;
import com.testmanagement.services.SubCategoryService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;
    private CategoryService categoryService;
    private SubCategoryService subCategoryService;
    private SubCategoryRepository subCategoryRepository;

    public ExamServiceImpl(ExamRepository examRepository, CategoryService categoryService,
            SubCategoryService subCategoryService, SubCategoryRepository subCategoryRepository) {
        this.examRepository = examRepository;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public Exam createMultipleChoiceQuestion(Exam exam) {
        if (!examRepository.existsByQuestion(exam.getQuestion())) {
            if (subCategoryRepository.existsById(exam.getSubCategory().getSubCategoryId())) {
                log.info("Question added successfully");
                return examRepository.save(exam);
            }
            throw new ExceptionManager("Subcategory not found");
        }
        log.error("Data already present in given id");
        throw new ExceptionManager("this question already present in database");
    }

    @Override
    public List<Exam> viewAllQuestions() {
        List<Exam> examList = examRepository.findAll();
        if (examList != null){
            return examList;
        }

        log.error("Failed to fetch questions: List is null");
        throw new ExceptionManager("List is Null ");

    }

    @Override
    public Optional<Exam> viewQuestionById(Integer questionId) {
        if (examRepository.existsById(questionId)){
            return examRepository.findById(questionId);
        }
        log.error("Exam Id Not present");
        throw new ExceptionManager("this Question id not present in database");
    }

    @Override
    public void deleteQuestionById(Integer questionId) {
        if (examRepository.existsById(questionId)) {
            examRepository.deleteById(questionId);
            log.info("Question deleted successfully: {}", questionId);
        }
        log.error("Question deleted Not successfully: {}", questionId);
        throw new ExceptionManager("Question id not found ");

    }

    @Override
    public Exam updateQuestion(Integer questionId, Exam updatedExam) {
        if (examRepository.existsById(questionId)) {
            if (subCategoryRepository.existsById(updatedExam.getSubCategory().getSubCategoryId()))
                return examRepository.save(updatedExam);

            throw new ExceptionManager("subcategory id not found ");
        }
        log.error("Question Not Updated", questionId);
        throw new ExceptionManager("data not found");
    }

    @Override
    public void processExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
                Workbook workbook = new HSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);

                String categoryName = getStringCellValue(row.getCell(1));
                String subCategoryName = getStringCellValue(row.getCell(2));
                String question = getStringCellValue(row.getCell(3));
                String option1 = getStringCellValue(row.getCell(4));
                String option2 = getStringCellValue(row.getCell(5));
                String option3 = getStringCellValue(row.getCell(6));
                String option4 = getStringCellValue(row.getCell(7));
                String ans = getStringCellValue(row.getCell(8));
                String positiveMark = String.valueOf(row.getCell(9).getNumericCellValue());
                String negativeMark = String.valueOf(row.getCell(10).getNumericCellValue());

                if (categoryService.checkCategory(categoryName)) {
                    Optional<SubCategory> subCategoryOptional = subCategoryService.getSubCategoryData(subCategoryName);
                    if (subCategoryOptional.isPresent()) {
                        SubCategory subCategory = subCategoryOptional.get();

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
                    } else {
                        log.error("SubCategory '{}' not found in database", subCategoryName);
                    }
                } else {
                    log.error("Category '{}' not found in database", categoryName);
                }
            }
        } catch (IOException e) {
            log.error("Error processing Excel file: {}", e.getMessage());
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return null;
        }
    }

}

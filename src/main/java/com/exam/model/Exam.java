package com.exam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="exam")
public class Exam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_id")
    private int question_id;

    @ManyToOne
    @JoinColumn(name = "sub_categoryid")
    private SubCategory subCategory;

    @Column(name="question")
    private String question;

    @Column(name="option_one")
    private String option1;

    @Column(name="option_two")
    private String option2;

    @Column(name="option_three")
    private String option3;

    @Column(name="option_four")
    private String option4;

    @Column(name="ans")
    private String ans;

    @Column(name="positive_mark")
    private String positiveMark;

    @Column(name="negative_mark")
    private String negativeMark;


}

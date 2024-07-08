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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="subcategory")
public class SubCategory {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="sub_categoryid")
    private int subCategoryID;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name="sub_category_name")
    private String subCategoryName;

    @Column(name="sub_category_dis")
    private String subCategoryDis;

   
    

}

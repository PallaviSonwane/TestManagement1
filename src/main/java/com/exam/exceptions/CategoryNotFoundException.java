package com.exam.exceptions;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(String msg){
        super(msg);
    }
}

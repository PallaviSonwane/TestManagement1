package com.exam.exception;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(String msg){
        super(msg);
    }
}

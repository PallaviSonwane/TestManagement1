package com.exam.exception;

public class DuplicateCategoryEntry extends RuntimeException {
    public DuplicateCategoryEntry(String msg){
        super(msg);
    }
}

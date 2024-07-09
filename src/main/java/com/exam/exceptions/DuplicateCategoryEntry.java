package com.exam.exceptions;

public class DuplicateCategoryEntry extends RuntimeException {
    public DuplicateCategoryEntry(String msg){
        super(msg);
    }
}

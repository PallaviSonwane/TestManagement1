package com.exam.exception;

public class DuplicateSubCategoryEntry extends RuntimeException {

    public DuplicateSubCategoryEntry(String msg)
    {
        super(msg);
    }
}

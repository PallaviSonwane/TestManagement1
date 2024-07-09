package com.exam.exceptions;

public class DuplicateSubCategoryEntry extends RuntimeException {

    public DuplicateSubCategoryEntry(String msg)
    {
        super(msg);
    }
}

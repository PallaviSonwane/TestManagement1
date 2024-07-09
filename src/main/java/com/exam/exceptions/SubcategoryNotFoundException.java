package com.exam.exceptions;

public class SubcategoryNotFoundException extends RuntimeException{
    public SubcategoryNotFoundException(String message)
    {
        super(message);
    }
}

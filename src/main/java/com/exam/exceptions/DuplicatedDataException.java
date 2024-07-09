package com.exam.exceptions;

public class DuplicatedDataException extends RuntimeException {

    public DuplicatedDataException(String msg)
    {
        super(msg);
    }
}

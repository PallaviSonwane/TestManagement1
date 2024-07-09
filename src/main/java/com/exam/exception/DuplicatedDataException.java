package com.exam.exception;

public class DuplicatedDataException extends RuntimeException {

    public DuplicatedDataException(String msg)
    {
        super(msg);
    }
}

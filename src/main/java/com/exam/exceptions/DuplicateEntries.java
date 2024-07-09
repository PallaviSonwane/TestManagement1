package com.exam.exceptions;

import java.util.List;

public class DuplicateEntries extends RuntimeException {

    public List<String> queList;
    public DuplicateEntries(List<String> questions){
        queList=questions;
    }
}

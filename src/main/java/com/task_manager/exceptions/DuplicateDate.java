package com.task_manager.exceptions;

public class DuplicateDate extends RuntimeException {

    public DuplicateDate(String message) {super(message);}

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

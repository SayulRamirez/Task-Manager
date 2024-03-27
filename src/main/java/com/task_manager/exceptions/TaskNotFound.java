package com.task_manager.exceptions;

public class TaskNotFound extends RuntimeException {
    public TaskNotFound(String message) {super(message);}

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

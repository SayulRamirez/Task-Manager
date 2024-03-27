package com.task_manager.exceptions;

public class AuthorNotFound extends RuntimeException {

    public AuthorNotFound(String message){super(message);}

    @Override
    public String getMessage() {return super.getMessage();}
}

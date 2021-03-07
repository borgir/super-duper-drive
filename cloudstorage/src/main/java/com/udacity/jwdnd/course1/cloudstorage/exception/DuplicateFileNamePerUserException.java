package com.udacity.jwdnd.course1.cloudstorage.exception;

public class DuplicateFileNamePerUserException extends Exception {

    public DuplicateFileNamePerUserException(String errorMessage) {
        super(errorMessage);
    }

}

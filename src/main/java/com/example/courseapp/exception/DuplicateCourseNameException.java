package com.example.courseapp.exception;

public class DuplicateCourseNameException extends RuntimeException {
    public DuplicateCourseNameException(String message) {
        super(message);
    }
}

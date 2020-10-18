package ru.progwards.tasktracker.controller.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundProjectException.class)
    public String notFoundProjectException(NotFoundProjectException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NullProjectException.class)
    public String nullPointerException(NullProjectException ex) {
        return ex.getMessage();
    }
}

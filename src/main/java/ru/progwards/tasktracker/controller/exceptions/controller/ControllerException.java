package ru.progwards.tasktracker.controller.exceptions.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.progwards.tasktracker.controller.exceptions.NotFoundProjectException;
import ru.progwards.tasktracker.controller.exceptions.NullObjectException;

@RestControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundProjectException.class)
    public String getMessage(NotFoundProjectException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NullObjectException.class)
    public String getMessage(NullObjectException ex) {
        return ex.getMessage();
    }
}

package ru.progwards.tasktracker.controller.exception.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.controller.exception.*;

@ControllerAdvice
public class GlobalTaskAdviceController {

    @ResponseBody
    @ExceptionHandler(IdBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String idBadRequestHandler(IdBadRequestException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TaskByIdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String taskByIdNotFoundHandler(TaskByIdNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TaskNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String taskNotExistHandler(TaskNotExistException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TasksNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tasksNotFoundHandler(TasksNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UpdateFieldNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String updateFieldNotExistHandler(UpdateFieldNotExistException ex) {
        return ex.getMessage();
    }

}

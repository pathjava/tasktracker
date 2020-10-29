package ru.progwards.tasktracker.controller.exception.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.controller.exception.*;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String idBadRequestHandler(BadRequestException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String taskByIdNotFoundHandler(NotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String taskNotExistHandler(NotExistException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(FieldNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String updateFieldNotExistHandler(FieldNotExistException ex) {
        return ex.getMessage();
    }

}

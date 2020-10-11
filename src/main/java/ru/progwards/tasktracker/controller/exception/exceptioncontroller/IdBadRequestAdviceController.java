package ru.progwards.tasktracker.controller.exception.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.controller.exception.IdBadRequestException;

@ControllerAdvice
public class IdBadRequestAdviceController {

    @ResponseBody
    @ExceptionHandler(IdBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String idBadRequestHandler(IdBadRequestException ex) {
        return ex.getMessage();
    }

}

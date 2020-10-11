package ru.progwards.tasktracker.controller.exception.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.controller.exception.UpdateFieldNotExistException;

@ControllerAdvice
public class UpdateFieldNotExistAdviceController {

    @ResponseBody
    @ExceptionHandler(UpdateFieldNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String updateFieldNotExistHandler(UpdateFieldNotExistException ex) {
        return ex.getMessage();
    }

}

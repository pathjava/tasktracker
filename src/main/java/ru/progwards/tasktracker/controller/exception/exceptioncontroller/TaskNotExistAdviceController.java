package ru.progwards.tasktracker.controller.exception.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.controller.exception.TaskNotExistException;

@ControllerAdvice
public class TaskNotExistAdviceController {

    @ResponseBody
    @ExceptionHandler(TaskNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String taskNotExistHandler(TaskNotExistException ex) {
        return ex.getMessage();
    }
}

package ru.progwards.tasktracker.controller.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.controller.exception.TaskNotFoundException;

@ControllerAdvice
public class TaskNotFoundAdviceController {

    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String taskNotFoundHandler(TaskNotFoundException ex) {
        return ex.getMessage();
    }

}

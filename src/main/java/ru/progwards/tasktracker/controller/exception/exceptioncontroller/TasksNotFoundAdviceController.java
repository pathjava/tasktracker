package ru.progwards.tasktracker.controller.exception.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.controller.exception.TasksNotFoundException;

@ControllerAdvice
public class TasksNotFoundAdviceController {

    @ResponseBody
    @ExceptionHandler(TasksNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tasksNotFoundHandler(TasksNotFoundException ex) {
        return ex.getMessage();
    }

}

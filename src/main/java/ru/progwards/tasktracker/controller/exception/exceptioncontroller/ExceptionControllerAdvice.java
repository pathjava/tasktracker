package ru.progwards.tasktracker.controller.exception.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.DeletionIsNotPossibleException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;

/**
 * контроллер для обработки кастомных исключений
 *
 * @author Oleg Kiselev
 */
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
    @ExceptionHandler(DeletionIsNotPossibleException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String taskByIdNotFoundHandler(DeletionIsNotPossibleException ex) {
        return ex.getMessage();
    }

}

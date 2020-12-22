package ru.progwards.tasktracker.exception.exceptioncontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;

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
    public String badRequestHandler(BadRequestException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundHandler(NotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OperationIsNotPossibleException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String operationIsNotPossibleHandler(OperationIsNotPossibleException ex) {
        return ex.getMessage();
    }

}

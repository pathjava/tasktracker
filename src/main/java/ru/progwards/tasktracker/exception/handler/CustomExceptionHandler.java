package ru.progwards.tasktracker.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;

import java.time.LocalDateTime;

/**
 * Контроллер для обработки кастомных исключений
 *
 * @author Oleg Kiselev
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Метод обработки исключения NotFoundException
     *
     * @param ex исключение NotFoundException
     * @return сообщение об ошибке
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        ApiError errors = new ApiError(
                LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(errors, errors.getStatus());
    }

    /**
     * Метод обработки исключения BadRequestException
     *
     * @param ex исключение BadRequestException
     * @return сообщение об ошибке
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        ApiError errors = new ApiError(
                LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(errors, errors.getStatus());
    }

    /**
     * Метод обработки исключения OperationIsNotPossibleException
     *
     * @param ex исключение OperationIsNotPossibleException
     * @return сообщение об ошибке
     */
    @ExceptionHandler(OperationIsNotPossibleException.class)
    public ResponseEntity<ApiError> handleOperationIsNotPossible(OperationIsNotPossibleException ex) {
        ApiError errors = new ApiError(
                LocalDateTime.now(), HttpStatus.NO_CONTENT, ex.getLocalizedMessage(), ex.getLocalizedMessage()
        );
        return new ResponseEntity<>(errors, errors.getStatus());
    }
}

package ru.progwards.tasktracker.controller.exception;

/**
 * исключение, используемое при ситуациях с неверными параметрами запроса
 *
 * @author Oleg Kiselev
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String s) {
        super(s);
    }
}

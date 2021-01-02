package ru.progwards.tasktracker.exception;

/**
 * Исключение, используемое при ситуациях с неверными параметрами запроса
 *
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String s) {
        super(s);
    }
}

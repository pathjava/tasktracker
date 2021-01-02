package ru.progwards.tasktracker.exception;

/**
 * Исключение, используемое, когда запрашиваемый объект не найден
 *
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String s) {
        super(s);
    }
}

package ru.progwards.tasktracker.controller.exception;

public class CreatedIsNotPossibleException extends RuntimeException {
    public CreatedIsNotPossibleException(String message) {
        super(message);
    }
}

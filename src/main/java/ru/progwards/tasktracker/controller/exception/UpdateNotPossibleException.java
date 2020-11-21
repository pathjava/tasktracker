package ru.progwards.tasktracker.controller.exception;

public class UpdateNotPossibleException extends RuntimeException {

    public UpdateNotPossibleException(String message) {
        super(message);
    }
}

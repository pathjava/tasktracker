package ru.progwards.tasktracker.controller.exception;

public class DeletionNotPossibleException extends RuntimeException {

    public DeletionNotPossibleException(String message) {
        super(message);
    }
}

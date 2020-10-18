package ru.progwards.tasktracker.controller.exceptions;

public class NullProjectException extends RuntimeException {

    public NullProjectException(String message) {
        super(message);
    }
}

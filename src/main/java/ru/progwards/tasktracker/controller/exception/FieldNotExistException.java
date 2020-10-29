package ru.progwards.tasktracker.controller.exception;

public class FieldNotExistException extends RuntimeException {
    public FieldNotExistException(String s) {
        super(s);
    }
}

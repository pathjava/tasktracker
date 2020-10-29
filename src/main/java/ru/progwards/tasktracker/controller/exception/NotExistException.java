package ru.progwards.tasktracker.controller.exception;

public class NotExistException extends RuntimeException {
    public NotExistException(String s) {
        super(s);
    }
}

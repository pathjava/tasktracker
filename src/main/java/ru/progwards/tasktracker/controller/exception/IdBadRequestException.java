package ru.progwards.tasktracker.controller.exception;

public class IdBadRequestException extends RuntimeException {
    public IdBadRequestException(Long id) {
        super("Id: " + id + " не задан или задан неверно!");
    }
}

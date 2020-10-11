package ru.progwards.tasktracker.controller.exception;

public class TaskNotExistException extends RuntimeException {
    public TaskNotExistException() {
        super("Задача не существует!");
    }
}

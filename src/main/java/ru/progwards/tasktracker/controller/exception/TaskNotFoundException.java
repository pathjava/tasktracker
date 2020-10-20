package ru.progwards.tasktracker.controller.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String id) {
        super(id);
    }
}

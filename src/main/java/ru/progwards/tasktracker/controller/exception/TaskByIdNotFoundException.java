package ru.progwards.tasktracker.controller.exception;

public class TaskByIdNotFoundException extends RuntimeException {
    public TaskByIdNotFoundException(Long id) {
        super("Задача с id: " + id + " не найдена!");
    }
}

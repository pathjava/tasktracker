package ru.progwards.tasktracker.controller.exception;

public class TasksNotFoundException extends RuntimeException {
    public TasksNotFoundException() {
        super("Список задач пустой!");
    }
}

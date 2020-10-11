package ru.progwards.tasktracker.controller.exception;

public class UpdateFieldNotExistException extends RuntimeException {
    public UpdateFieldNotExistException() {
        super("Значение обновляемого поля отсутствует!");
    }
}

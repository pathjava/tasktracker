package ru.progwards.tasktracker.controller.exception;

/**
 * исключение, используемое при невозможности удаления объекта
 *
 */
public class OperationIsNotPossibleException extends RuntimeException {
    public OperationIsNotPossibleException(String s){
        super(s);
    }
}

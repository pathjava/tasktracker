package ru.progwards.tasktracker.exception;

/**
 * Исключение, используемое при невозможности удаления объекта
 *
 */
public class OperationIsNotPossibleException extends RuntimeException {
    public OperationIsNotPossibleException(String s){
        super(s);
    }
}

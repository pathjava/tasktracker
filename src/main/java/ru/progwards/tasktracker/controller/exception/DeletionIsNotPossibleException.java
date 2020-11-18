package ru.progwards.tasktracker.controller.exception;

/**
 * исключение, используемое при невозможности удаления объекта
 *
 * @author Oleg Kiselev
 */
public class DeletionIsNotPossibleException extends RuntimeException {
    public DeletionIsNotPossibleException(String s){
        super(s);
    }
}

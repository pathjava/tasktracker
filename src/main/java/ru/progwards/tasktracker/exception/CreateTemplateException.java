package ru.progwards.tasktracker.exception;

/**
 * Исключение, используемое если лист шаблона пустой
 *
 */

public class CreateTemplateException extends RuntimeException {
    public CreateTemplateException(String s){
        super(s);
    }
}

package ru.progwards.tasktracker.service;

/**
 * Создать бизнес-объект по шаблону
 *
 * @param <M> тип бизнес-объекта
 */
public interface TemplateService<M> {

    void makeFromTemplate(Object... args);

}
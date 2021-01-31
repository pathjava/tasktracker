package ru.progwards.tasktracker.service;

import java.util.List;

/**
 * Создать бизнес-объект по шаблону
 *
 * @param <M> тип бизнес-объекта
 */
public interface TemplateService<M> {

    List<M> createFromTemplate(Object... args);

}
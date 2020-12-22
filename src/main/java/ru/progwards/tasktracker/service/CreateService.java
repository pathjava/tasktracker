package ru.progwards.tasktracker.service;

/**
 * Создание бизнес-объекта
 *
 * @param <M> тип бизнес-объекта
 */
public interface CreateService<M> {

    void create(M model);

}

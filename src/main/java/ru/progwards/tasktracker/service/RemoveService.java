package ru.progwards.tasktracker.service;

/**
 * Удалить бизнес-объект
 *
 * @param <M> тип бизнес-объекта
 */
public interface RemoveService<M> {

    void remove(M model);

}

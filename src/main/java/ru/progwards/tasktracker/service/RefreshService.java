package ru.progwards.tasktracker.service;

/**
 * Обновить бизнес-объект в хранилище
 *
 * @param <M> тип бизнес-объекта
 */
public interface RefreshService<M> {

    void refresh(M model);

}

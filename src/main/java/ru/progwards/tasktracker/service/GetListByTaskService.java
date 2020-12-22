package ru.progwards.tasktracker.service;

import java.util.Collection;

/**
 * Получения списка бизнес-объектов для данного идентификатора задачи
 *
 * @param <T> тип идентификатора
 * @param <M> тип бизнес-объекта
 */
public interface GetListByTaskService<T, M> {

    Collection<M> getListByTaskId(T taskId);

}

package ru.progwards.tasktracker.service;

import java.util.Collection;

/**
 * Получение списка для данного идентификатора задачи
 *
 * @param <T> тип идентификатора (Long)
 * @param <M> бизнес-объект в списке
 */
public interface GetListByAttachedTaskId<T, M> {

    Collection<M> getListByAttachedTaskId(T taskId);

}

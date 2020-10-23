package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

/**
 * Получения списка для данного идентификатора задачи
 *
 * @param <T> тип идентификатора (Long)
 * @param <M> бизнес-объект в списке
 */
public interface GetListByTaskService<T, M> {

    Collection<M> getListByTaskId(T taskId);

}

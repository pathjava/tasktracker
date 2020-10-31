package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

/**
 * Получение списка для данного идентификатора проекта
 *
 * @param <T> тип идентификатора (Long)
 * @param <M> список бизнес-объектов
 */
public interface GetListByProjectService<T, M> {

    Collection<M> getListByProjectId(T projectId);

}

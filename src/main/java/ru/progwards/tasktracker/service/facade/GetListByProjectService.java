package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

/**
 * Получение списка бизнес-объектов для данного идентификатора проекта
 *
 * @param <T> тип идентификатора
 * @param <M> тип бизнес-объекта
 */
public interface GetListByProjectService<T, M> {

    Collection<M> getListByProjectId(T projectId);

}

package ru.progwards.tasktracker.service;

import java.util.Collection;

/**
 * Получения списка бизнес-объектов для данного идентификатора родителя
 *
 * @param <T> тип идентификатора
 * @param <M> тип бизнес-объекта
 */
@Deprecated
public interface GetListByParentService<T, M> {

    Collection<M> getListByParentId(T parentId);

}

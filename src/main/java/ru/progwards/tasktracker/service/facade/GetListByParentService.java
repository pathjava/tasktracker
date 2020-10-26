package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

/**
 * Получения списка для данного идентификатора родителя
 *
 * @param <T> тип идентификатора (Long)
 * @param <M> бизнес-объект в списке
 */
public interface GetListByParentService<T, M> {

    Collection<M> getListByParentId(T parentId);

}

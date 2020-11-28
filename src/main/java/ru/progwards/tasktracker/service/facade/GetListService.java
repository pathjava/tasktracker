package ru.progwards.tasktracker.service.facade;

import java.util.Collection;

/**
 * Получить все бизнес-объекты из хранилища
 *
 * @param <M> тип бизнес-объекта
 */
public interface GetListService<M> {

    Collection<M> getList();

}

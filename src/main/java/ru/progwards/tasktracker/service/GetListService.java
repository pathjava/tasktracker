package ru.progwards.tasktracker.service;

import java.util.List;

/**
 * Получить все бизнес-объекты из хранилища
 *
 * @param <M> тип бизнес-объекта
 */
public interface GetListService<M> {

    List<M> getList();

}

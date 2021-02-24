package ru.progwards.tasktracker.service;

import java.util.List;
import java.util.Map;

/**
 * Получить все бизнес-объекты из хранилища
 *
 * @param <M> тип бизнес-объекта
 */
public interface GetListParamService<M> {

    List<M> getListParam(Map<String, String> params);

}

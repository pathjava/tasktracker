package ru.progwards.tasktracker.service;

import ru.progwards.tasktracker.model.UpdateOneValue;

/**
 * Изменить одно свойство бизнес-объекта
 *
 * @param <T> тип бизнес-объекта
 */
public interface UpdateOneFieldService<T> {

    void updateOneField(UpdateOneValue oneValue);

}

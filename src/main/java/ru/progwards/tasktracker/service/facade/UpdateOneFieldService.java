package ru.progwards.tasktracker.service.facade;

import ru.progwards.tasktracker.service.vo.UpdateOneValue;

/**
 * Изменить одно свойство бизнес-объекта
 *
 * @param <T> тип бизнес-объекта
 */
public interface UpdateOneFieldService<T> {

    void updateOneField(UpdateOneValue oneValue);

}

package ru.progwards.tasktracker.service.facade;

import ru.progwards.tasktracker.service.vo.UpdateOneValue;

public interface UpdateOneFieldService<T> {

    void updateOneField(UpdateOneValue oneValue);

}

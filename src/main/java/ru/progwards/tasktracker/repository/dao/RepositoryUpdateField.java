package ru.progwards.tasktracker.repository.dao;

import ru.progwards.tasktracker.service.vo.UpdateOneValue;

public interface RepositoryUpdateField<T> {
    void updateField(UpdateOneValue oneValue);
}

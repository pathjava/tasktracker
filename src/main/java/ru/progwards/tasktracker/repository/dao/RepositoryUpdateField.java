package ru.progwards.tasktracker.repository.dao;

import ru.progwards.tasktracker.service.vo.UpdateOneValue;

public interface RepositoryUpdateField {
    void updateField(UpdateOneValue oneValue);
}

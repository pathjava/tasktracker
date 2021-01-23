package ru.progwards.tasktracker.repository.deprecated;

import ru.progwards.tasktracker.model.UpdateOneValue;

@Deprecated
public interface RepositoryUpdateField<T> {
    void updateField(UpdateOneValue oneValue);
}

package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.TaskRepositoryUpdateField;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;
import ru.progwards.tasktracker.service.facade.OneFieldSetService;

@Service
public class TaskOneFieldSetService implements OneFieldSetService {

    private TaskRepositoryUpdateField updateField;

    @Autowired
    public void setUpdateField(TaskRepositoryUpdateField updateField) {
        this.updateField = updateField;
    }

    @Override
    public void setOneField(UpdateOneValue oneValue) {
        updateField.updateField(oneValue);
    }
}

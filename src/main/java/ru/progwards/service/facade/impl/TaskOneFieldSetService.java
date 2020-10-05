package ru.progwards.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.repository.dao.impl.TaskRepositoryUpdateField;
import ru.progwards.service.facade.OneFieldSetService;
import ru.progwards.service.vo.UpdateOneValue;

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

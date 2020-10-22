package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepositoryUpdateField;
import ru.progwards.tasktracker.service.facade.OneFieldSetService;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

@Component
public class ProjectOneFieldSetService implements OneFieldSetService {

    private final ProjectEntityRepositoryUpdateField projectEntityRepositoryUpdateField;

    public ProjectOneFieldSetService(ProjectEntityRepositoryUpdateField projectEntityRepositoryUpdateField) {
        this.projectEntityRepositoryUpdateField = projectEntityRepositoryUpdateField;
    }

    @Override
    public void setOneField(UpdateOneValue oneValue) {
        projectEntityRepositoryUpdateField.updateField(oneValue);
    }
}
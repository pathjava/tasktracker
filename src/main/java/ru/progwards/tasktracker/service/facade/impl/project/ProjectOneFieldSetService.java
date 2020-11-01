package ru.progwards.tasktracker.service.facade.impl.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.facade.OneFieldSetService;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

/**
 * Класс по обновлению поля у бизнес-модели Project
 * @author Pavel Khovaylo
 */
@Service
public class ProjectOneFieldSetService implements OneFieldSetService {

    /**
     * класс, по обновлению поля у сущности ProjectEntity
     */
    @Autowired
    private RepositoryUpdateField<ProjectEntity> projectEntityRepositoryUpdateField;

    /**
     * метод обновляет поле у бизнес-модели Project
     * @param oneValue объект с данными по текущему обновлению
     */
    @Override
    public void setOneField(UpdateOneValue oneValue) {
        projectEntityRepositoryUpdateField.updateField(oneValue);
    }
}
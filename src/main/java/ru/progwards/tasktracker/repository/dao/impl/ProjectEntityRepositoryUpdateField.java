package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.lang.reflect.Field;

@Component
public class ProjectEntityRepositoryUpdateField implements RepositoryUpdateField {

    private final ProjectEntityRepository repository;

    public ProjectEntityRepositoryUpdateField(ProjectEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateField(UpdateOneValue oneValue) {
        ProjectEntity entity = repository.get(oneValue.getId());
        String field = oneValue.getFieldName();

        Class<ProjectEntity> clazz = ProjectEntity.class;

        for (Field f : clazz.getDeclaredFields()) {
            if (f.getName().equals(field)) {
                f.setAccessible(true);
                try {
                    f.set(entity, oneValue.getNewValue());
                    repository.update(entity);
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

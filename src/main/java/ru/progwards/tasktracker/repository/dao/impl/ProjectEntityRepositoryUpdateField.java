package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.lang.reflect.Field;

/**
 * Класс, позволяющий обновить поле у проекта
 * @author Pavel Khovaylo
 */
@Component
public class ProjectEntityRepositoryUpdateField implements RepositoryUpdateField<ProjectEntity> {

    /**
     * репозиторий с проектами
     */
    @Autowired
    private Repository<Long, ProjectEntity> repository;

    /**
     * метод позволяет обновить значение любого поля проекта
     * @param oneValue объект с данными по текущему обновлению
     */
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
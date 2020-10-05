package ru.progwards.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.repository.dao.RepositoryUpdateField;
import ru.progwards.repository.entity.TaskEntity;
import ru.progwards.service.vo.UpdateOneValue;

import java.lang.reflect.Field;

@Component
public class TaskRepositoryUpdateField implements RepositoryUpdateField {

    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void updateField(UpdateOneValue oneValue) {
        TaskEntity entity = taskRepository.get(oneValue.getId());
        String field = oneValue.getFieldName();

        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            if (declaredField.getName().equals(field)) {
                declaredField.setAccessible(true);
                try {
                    declaredField.set(entity, oneValue.getNewValue());
                    taskRepository.update(entity);
                    break;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

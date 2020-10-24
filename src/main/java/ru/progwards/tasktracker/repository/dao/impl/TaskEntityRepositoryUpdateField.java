package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.lang.reflect.Field;

@Component
public class TaskEntityRepositoryUpdateField implements RepositoryUpdateField<TaskEntity> {

    private TaskEntityRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskEntityRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * @param oneValue объект, содержащий идентификатор и тип и значение обновляемого поля задачи
     */
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

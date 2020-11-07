package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;
import ru.progwards.tasktracker.service.facade.UpdateOneFieldService;

/**
 * Бизнес-логика обновления одного поля
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskUpdateOneFieldService implements UpdateOneFieldService<Task> {

    private final RepositoryUpdateField<TaskEntity> updateField;

    @Autowired
    public TaskUpdateOneFieldService(TaskEntityRepositoryUpdateField updateField){
        this.updateField = updateField;
    }

    /**
     * метод обновления поля задачи
     *
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     */
    @Override
    public void updateOneField(UpdateOneValue oneValue) {
        updateField.updateField(oneValue);
    }
}

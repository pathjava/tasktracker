package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.facade.UpdateOneFieldService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

/**
 * Бизнес-логика обновления одного поля задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskUpdateOneFieldService implements UpdateOneFieldService<Task> {

    @Qualifier("taskEntityRepository")
    @Autowired
    private RepositoryUpdateField<TaskEntity> repository;

    /**
     * Метод обновления поля задачи
     *
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     */
    @Override
    public void updateOneField(UpdateOneValue oneValue) {
        repository.updateField(oneValue);
    }
}

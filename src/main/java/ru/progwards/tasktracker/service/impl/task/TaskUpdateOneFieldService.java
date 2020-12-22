package ru.progwards.tasktracker.service.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.deprecated.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
import ru.progwards.tasktracker.service.UpdateOneFieldService;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.UpdateOneValue;

/**
 * Бизнес-логика обновления одного поля задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskUpdateOneFieldService implements UpdateOneFieldService<Task> {

    //@Qualifier("taskRepository")
    //@Autowired
    private RepositoryUpdateField<TaskEntity> repository;

    /**
     * Метод обновления поля задачи
     *
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     */
    @Override
    public void updateOneField(UpdateOneValue oneValue) {
        if (oneValue.getFieldName().equals("code"))
            throw new OperationIsNotPossibleException("Обновление поля: " + oneValue.getFieldName() + " невозможно!");
        repository.updateField(oneValue);
    }
}

package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.Task;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Бизнес-логика обновления задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskRefreshService implements RefreshService<Task> {

    @Autowired
    private Repository<Long, TaskEntity> repository;
    @Autowired
    private Converter<TaskEntity, Task> converter;

    /**
     * Метод обновления задачи
     *
     * @param model  value object - объект бизнес логики (задача), который необходимо обновить
     */
    @Override
    public void refresh(Task model) {
        if (model.getTimeLeft().isNegative())
            model.setTimeLeft(Duration.ZERO);

        model.setUpdated(ZonedDateTime.now());

        repository.update(converter.toEntity(model));
    }
}

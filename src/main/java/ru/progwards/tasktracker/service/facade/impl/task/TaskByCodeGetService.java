package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * Бизнес-логика получения задачи по коду
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskByCodeGetService implements GetService<String, Task> {

    private Repository<String, TaskEntity> repository;
    private Converter<TaskEntity, Task> converter;

    @Autowired
    public void setRepository(Repository<String, TaskEntity> repository) {
        this.repository = repository;
    }

    @Autowired
    public void setConverter(Converter<TaskEntity, Task> converter) {
        this.converter = converter;
    }

    /**
     * @param code строковое значение кода задачи, создаваемое на основе префикса проекта задачи
     * @return найденную задачу или пусто
     */
    @Override
    public Task get(String code) {
        return code == null ? null : converter.toVo(repository.get(code));
    }
}

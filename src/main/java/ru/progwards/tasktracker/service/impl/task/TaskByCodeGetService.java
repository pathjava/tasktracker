package ru.progwards.tasktracker.service.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByCode;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.Task;

/**
 * Бизнес-логика получения задачи по коду
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskByCodeGetService implements GetService<String, Task> {

    @Autowired
    private RepositoryByCode<String, TaskEntity> repositoryByCode;
    @Autowired
    private Converter<TaskEntity, Task> converter;

    /**
     * Метод получения задачи по текстовому коду задачи
     *
     * @param code строковое значение кода задачи, создаваемое на основе префикса проекта задачи
     * @return найденную задачу или пусто
     */
    @Override
    public Task get(String code) {
        return code == null ? null : converter.toVo(repositoryByCode.getByCode(code));
    }
}

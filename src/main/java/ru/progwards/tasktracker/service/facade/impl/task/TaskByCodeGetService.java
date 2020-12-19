package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.RepositoryByCode;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

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

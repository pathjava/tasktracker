package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * получение задачи по коду
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskByCodeGetService implements GetService<String, Task> {

    private Repository<String, TaskEntity> taskRepository;
    private Converter<TaskEntity, Task> converterTask;

    @Autowired
    public void setTaskRepository(Repository<String, TaskEntity> taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setConverterTask(Converter<TaskEntity, Task> converterTask) {
        this.converterTask = converterTask;
    }

    /**
     * @param code строковое значение кода задачи, создаваемое на основе префикса проекта задачи
     * @return найденную задачу или пусто
     */
    @Override
    public Task get(String code) {
        return code == null ? null : converterTask.toVo(taskRepository.get(code));
    }
}

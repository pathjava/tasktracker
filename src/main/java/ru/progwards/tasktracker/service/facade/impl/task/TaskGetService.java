package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.converter.impl.TaskConverter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

/**
 * Бизнес-логика получения задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskGetService implements GetService<Long, Task> {

    private Repository<Long, TaskEntity> taskRepository;
    private Converter<TaskEntity, Task> converterTask;

    @Autowired
    public void setTaskRepository(TaskEntityRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setConverterTask(TaskConverter converterTask) {
        this.converterTask = converterTask;
    }

    /**
     * @param id идентификатор по которому необходимо получить задачу
     * @return найденную задачу или пусто
     */
    @Override
    public Task get(Long id) {
        return id == null ? null : converterTask.toVo(taskRepository.get(id));
    }
}

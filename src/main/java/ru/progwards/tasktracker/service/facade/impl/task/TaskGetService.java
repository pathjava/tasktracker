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

    private Repository<Long, TaskEntity> repository;
    private Converter<TaskEntity, Task> converter;

    @Autowired
    public void setRepository(TaskEntityRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setConverter(TaskConverter converter) {
        this.converter = converter;
    }

    /**
     * @param id идентификатор по которому необходимо получить задачу
     * @return найденную задачу или пусто
     */
    @Override
    public Task get(Long id) {
        return id == null ? null : converter.toVo(repository.get(id));
    }
}

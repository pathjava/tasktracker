package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.converter.impl.TaskConverter;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Бизнес-логика получения списка задач
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskGetListService implements GetListService<Task> {

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
     * метод получения всех задач
     *
     * @return коллекцию задач (может иметь пустое значение)
     */
    @Override
    public Collection<Task> getList() {
        return repository.get().stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }
}

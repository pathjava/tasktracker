package ru.progwards.tasktracker.service.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Бизнес-логика получения списка задач
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskGetListService implements GetListService<Task> {

    @Autowired
    private Repository<Long, TaskEntity> repository;
    @Autowired
    private Converter<TaskEntity, Task> converter;

    /**
     * Метод получения всех задач без привязки к какому-либо проекту
     *
     * @return коллекцию задач (может иметь пустое значение)
     */
    @Override
    public List<Task> getList() {
        return repository.get().stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }
}

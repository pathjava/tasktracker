package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
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
    public Collection<Task> getList() {
        return repository.get().stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }
}

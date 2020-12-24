package ru.progwards.tasktracker.service.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
import ru.progwards.tasktracker.service.GetListByProjectService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Бизнес-логика получения задач по идентификатору проекта
 *
 * @author Oleg Kiselev
 */
@Service
@Deprecated
public class TaskGetListByProjectService implements GetListByProjectService<Long, Task> {

    @Autowired
    private Repository<Long, TaskEntity> repository;
    @Autowired
    private Converter<TaskEntity, Task> converter;

    /**
     * Метод получения коллекции задач по идентификатору проекта
     *
     * @param projectId идентификатор проекта, для которого делается выборка задач (не помеченных как удаленные)
     * @return коллекция задач (может иметь пустое значение)
     */
    @Override
    public List<Task> getListByProjectId(Long projectId) {
        return repository.get().stream()
                .filter(taskEntity -> taskEntity.getProject().getId().equals(projectId) && !taskEntity.isDeleted())
                .map(taskEntity -> converter.toVo(taskEntity))
                .collect(Collectors.toList());
    }
}

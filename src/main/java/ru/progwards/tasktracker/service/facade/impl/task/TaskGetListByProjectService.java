package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.converter.impl.TaskConverter;
import ru.progwards.tasktracker.service.facade.GetListByProjectService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * получение задач по идентификатору проекта
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskGetListByProjectService implements GetListByProjectService<Long, Task> {

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
     * @param projectId идентификатор проекта, для которого делается выборка задач (не помеченных как удаленные)
     * @return коллекция задач (может иметь пустое значение)
     */
    @Override
    public Collection<Task> getListByProjectId(Long projectId) {
        return taskRepository.get().stream()
                .filter(taskEntity -> taskEntity.getProject_id().equals(projectId) && !taskEntity.getDeleted())
                .map(taskEntity -> converterTask.toVo(taskEntity))
                .collect(Collectors.toList());
    }
}
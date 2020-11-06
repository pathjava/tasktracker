package ru.progwards.tasktracker.service.facade.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.Task;

import java.time.ZonedDateTime;
import java.util.Random;

/**
 * Бизнес-логика работы с задачей
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskCreateService implements CreateService<Task> {

    private Repository<Long, TaskEntity> taskRepository;
    private Converter<TaskEntity, Task> converterTask;
    private GetService<Long, Project> projectGetService;
    private RefreshService<Project> refreshService;

    @Autowired
    public void setTaskRepository(
            Repository<Long, TaskEntity> taskRepository,
            Converter<TaskEntity, Task> converterTask,
            GetService<Long, Project> projectGetService,
            RefreshService<Project> refreshService
    ) {
        this.taskRepository = taskRepository;
        this.converterTask = converterTask;
        this.projectGetService = projectGetService;
        this.refreshService = refreshService;
    }

    /**
     * метод создает задачу
     *
     * @param task value object
     */
    @Override
    public void create(Task task) {
        if (task.getId() == null) //TODO - for testing generate id
            task.setId(new Random().nextLong());
        if (task.getCode() == null)
            task.setCode(generateTaskCode(task.getProject_id()));
        if (task.getCreated() == null)
            task.setCreated(ZonedDateTime.now());

        taskRepository.create(converterTask.toEntity(task));
    }

    /**
     * @param project_id идентификатор проекта, к которому принадлежит задача
     * @return код задачи в формате "NGR-1"
     */
    private String generateTaskCode(Long project_id) {
        Project project = projectGetService.get(project_id);
        Long lastTaskCode = project.getLastTaskCode();
        String taskCode = project.getPrefix() + "-" + lastTaskCode;
        project.setLastTaskCode(lastTaskCode + 1);
        refreshService.refresh(project);
        return taskCode;
    }
}

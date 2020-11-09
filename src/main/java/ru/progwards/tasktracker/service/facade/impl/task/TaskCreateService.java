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

/**
 * Бизнес-логика работы с задачей
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskCreateService implements CreateService<Task> {

    private Repository<Long, TaskEntity> repository;
    private Converter<TaskEntity, Task> converter;
    private GetService<Long, Project> getService;
    private RefreshService<Project> refreshService;

    @Autowired
    public void setTaskRepository(
            Repository<Long, TaskEntity> taskRepository,
            Converter<TaskEntity, Task> converterTask,
            GetService<Long, Project> projectGetService,
            RefreshService<Project> refreshService
    ) {
        this.repository = taskRepository;
        this.converter = converterTask;
        this.getService = projectGetService;
        this.refreshService = refreshService;
    }

    /**
     * метод создает задачу
     *
     * @param model value object
     */
    @Override
    public void create(Task model) {
        if (model.getCode() == null)
            model.setCode(generateTaskCode(model.getProject_id()));
        if (model.getCreated() == null)
            model.setCreated(ZonedDateTime.now());

        repository.create(converter.toEntity(model));
    }

    /**
     * @param project_id идентификатор проекта, к которому принадлежит задача
     * @return код задачи в формате "NGR-1"
     */
    private String generateTaskCode(Long project_id) {
        Project project = getService.get(project_id);
        Long lastTaskCode = project.getLastTaskCode();
        String taskCode = project.getPrefix() + "-" + lastTaskCode;
        project.setLastTaskCode(lastTaskCode + 1);
        refreshService.refresh(project);
        return taskCode;
    }
}

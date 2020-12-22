package ru.progwards.tasktracker.service.impl.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RefreshService;
import ru.progwards.tasktracker.model.*;

import java.time.ZonedDateTime;

/**
 * Бизнес-логика создания задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskCreateService implements CreateService<Task> {

    @Autowired
    private Repository<Long, TaskEntity> repository;
    @Autowired
    private Converter<TaskEntity, Task> converter;
    @Autowired
    private GetService<Long, Project> getService;
    @Autowired
    private RefreshService<Project> refreshService;

    /**
     * Метод создает задачу
     * 1) создается текстовый код задачи на основе префикса проекта
     * 2) устанавливается время создания задачи
     * 3) устанавливается статус WorkFlowStatus
     *
     * @param model value object
     */
    @Override
    public void create(Task model) {
        if (model.getCode() == null)
            model.setCode(generateTaskCode(model.getProject().getId()));

        if (model.getCreated() == null)
            model.setCreated(ZonedDateTime.now());

        if (model.getType() != null) {
            WorkFlow workFlow = model.getType().getWorkFlow();
            if (workFlow != null)
                model.setStatus(workFlow.getStartStatus());
        }

        repository.create(converter.toEntity(model));
    }

    /**
     * Метод создания кода задачи на основе префикса проекта
     * Из бизнес-объекта проекта получаем крайний индекс задачи,
     * инкрементируем +1 и создаем код задачи, обновляем проект
     *
     * @param project_id идентификатор проекта, к которому принадлежит задача
     * @return код задачи в формате "NGR-1"
     */
    public String generateTaskCode(Long project_id) {
        Project project = getService.get(project_id);
        Long lastTaskCode = project.getLastTaskCode() + 1;
        String taskCode = project.getPrefix() + "-" + lastTaskCode;
        project.setLastTaskCode(lastTaskCode);
        refreshService.refresh(project);
        return taskCode;
    }
}

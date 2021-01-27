package ru.progwards.tasktracker.service.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskPriority;
import ru.progwards.tasktracker.repository.TaskPriorityRepository;
import ru.progwards.tasktracker.service.*;

import java.util.List;

/**
 * Сервисы для работы с TaskPriority
 * @author Pavel Khovaylo
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskPriorityService implements GetListService<TaskPriority>,
                                            GetService<Long,
                                            TaskPriority>,
                                            CreateService<TaskPriority>,
                                            RefreshService<TaskPriority>,
                                            RemoveService<TaskPriority>,
                                            TemplateService<TaskPriority> {

    /**
     * репозиторий с TaskPriorityEntity
     */
    TaskPriorityRepository repository;

    /**
     * метод по получению списка TaskPriority
     * @return список TaskPriority
     */
    @Override
    public List<TaskPriority> getList() {
        return repository.findAll();
    }
    /**
     * метод по получению TaskPriority
     * @param id идентификатор TaskPriority, который необходимо получить
     * @return TaskPriority
     */
    @Override
    public TaskPriority get(Long id) {
        return repository.findById(id).
                orElseThrow(() -> new OperationIsNotPossibleException("TaskPriority.id = " + id + " doesn't exist"));
    }

    /**
     * метот добавляет TaskPriority в репозиторий
     * @param model бизнес-модель
     */
    @Transactional
    @Override
    public void create(TaskPriority model) {
        repository.save(model);
    }

    /**
     * метод по обновлению TaskPriority
     * @param model TaskPriority, который хотим обновить
     */
    @Transactional
    @Override
    public void refresh(TaskPriority model) {
        repository.save(model);
    }

    /**
     * метод по удалению TaskPriority
     * @param model TaskPriority, который необходимо удалить
     */
    @Transactional
    @Override
    public void remove(TaskPriority model) {
        TaskPriority taskPriority = repository.findById(model.getId()).
                orElseThrow(() -> new OperationIsNotPossibleException("TaskPriority.id = " + model.getId() + " doesn't exist"));

        //TODO подумать как переделать код ниже

        List<Task> tasks = taskPriority.getTasks();

        boolean isExist = false;

        // если находим задачу с данным приоритетом, то удаление этого приоритета невозможно
        for (Task t : tasks) {
            if (t.getPriority().getId().equals(model.getId())) {
                isExist = true;
                break;
            }
        }

        if (!isExist)
            repository.delete(model);
        else
            throw new OperationIsNotPossibleException("TaskPriority with id = " + model.getId() + " delete not possible");
    }

    @Override
    public void createFromTemplate(Object... args) {
        if (args.length != 3)
            throw new OperationIsNotPossibleException("TaskPriority.createFromTemplate: creating test task-priority is impossible");
        if (!(args[0] instanceof String))
            throw new OperationIsNotPossibleException("TaskPriority.createFromTemplate: argument 0 must be String");
        if (!(args[1] instanceof List))
            throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 1 must be List<Task>");
        if (!(args[2] instanceof Integer) || (int) args[2] != 4)
            throw new OperationIsNotPossibleException("Project.createFromTemplate: argument 2 must be Integer and equals 4");

        int count = (int) args[2];

        for (int i = 1; i <= count; i++) {
            TaskPriority taskPriority = new TaskPriority();
            taskPriority.setName((String)args[0]);
            taskPriority.setValue(i);
            taskPriority.setTasks((List<Task>)args[1]);

            repository.save(taskPriority);
        }
    }
}
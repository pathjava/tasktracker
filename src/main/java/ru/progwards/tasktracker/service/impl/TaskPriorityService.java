package ru.progwards.tasktracker.service.impl;

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
public class TaskPriorityService implements GetListService<TaskPriority>,
        GetService<Long, TaskPriority>, CreateService<TaskPriority>,
        RefreshService<TaskPriority>, RemoveService<TaskPriority> {

    /**
     * репозиторий с TaskPriorityEntity
     */
    @Autowired
    private TaskPriorityRepository repository;

    @Autowired
    private GetListService<Task> taskGetListService;

    /**
     * метот добавляет TaskPriority в репозиторий
     * @param model бизнес-модель
     */
    @Transactional
    @Override
    public void create(TaskPriority model) {
        if (model == null)
            throw new OperationIsNotPossibleException("Create TaskPriority is not possible");
        else
            repository.save(model);
    }

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
     * метод по обновлению TaskPriority
     * @param model TaskPriority, который хотим обновить
     */
    @Transactional
    @Override
    public void refresh(TaskPriority model) {
        if (model == null)
            throw new OperationIsNotPossibleException("Create TaskPriority is not possible");

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
}
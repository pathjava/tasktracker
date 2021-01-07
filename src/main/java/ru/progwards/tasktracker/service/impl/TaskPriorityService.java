package ru.progwards.tasktracker.service.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskPriorityService implements GetListService<TaskPriority>,
        GetService<Long, TaskPriority>, CreateService<TaskPriority>,
        RefreshService<TaskPriority>, RemoveService<TaskPriority> {

    /**
     * репозиторий с TaskPriorityEntity
     */
    TaskPriorityRepository repository;

    /**
     * метот добавляет TaskPriority в репозиторий
     * @param model бизнес-модель
     */
    @Override
    public void create(TaskPriority model) {
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
    @Override
    public void refresh(TaskPriority model) {
        repository.save(model);
    }

    /**
     * метод по удалению TaskPriority
     * @param model TaskPriority, который необходимо удалить
     */
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
}
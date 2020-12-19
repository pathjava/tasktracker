package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.controller.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskPriorityEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.TaskPriority;

import java.util.Collection;
import java.util.stream.Collectors;

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
    private Repository<Long, TaskPriorityEntity> repository;
    /**
     * конвертер TaskPriority
     */
    @Autowired
    private Converter<TaskPriorityEntity, TaskPriority> converter;

    @Autowired
    private GetListService<Task> taskGetListService;

    /**
     * метот добавляет TaskPriority в репозиторий
     * @param model бизнес-модель
     */
    @Override
    public void create(TaskPriority model) {
        repository.create(converter.toEntity(model));
    }

    /**
     * метод по получению списка TaskPriority
     * @return список TaskPriority
     */
    @Override
    public Collection<TaskPriority> getList() {
        return repository.get().stream().map(e -> converter.toVo(e)).collect(Collectors.toList());
    }

    /**
     * метод по получению TaskPriority
     * @param id идентификатор TaskPriority, который необходимо получить
     * @return TaskPriority
     */
    @Override
    public TaskPriority get(Long id) {
        return converter.toVo(repository.get(id));
    }

    /**
     * метод по обновлению TaskPriority
     * @param model TaskPriority, который хотим обновить
     */
    @Override
    public void refresh(TaskPriority model) {
        repository.update(converter.toEntity(model));
    }

    /**
     * метод по удалению TaskPriority
     * @param model TaskPriority, который необходимо удалить
     */
    @Override
    public void remove(TaskPriority model) {
        Collection<Task> tasks = taskGetListService.getList();

        boolean isExist = false;

        // если находим задачу с данным приоритетом, то удаление этого приоритета невозможно
        for (Task t : tasks) {
            if (t.getPriority().getId().equals(model.getId())) {
                isExist = true;
                break;
            }
        }

        if (!isExist)
            repository.delete(model.getId());
        else
            throw new OperationIsNotPossibleException("TaskPriority with id = " + model.getId() + " delete not possible");
    }
}
package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.controller.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByProjectId;
import ru.progwards.tasktracker.repository.entity.TaskTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.TaskType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Бизнес-логика получения списка типов задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskTypeService implements CreateService<TaskType>, GetService<Long, TaskType>,
        RemoveService<TaskType>, RefreshService<TaskType>, GetListByProjectService<Long, TaskType> {

    @Autowired
    private Repository<Long, TaskTypeEntity> repository;
    @Autowired
    private RepositoryByProjectId<Long, TaskTypeEntity> byProjectId;
    @Autowired
    private Converter<TaskTypeEntity, TaskType> converter;
    @Autowired
    private GetListService<Task> getListService;

    //TODO ДОБАВИЛ ДЛЯ СВОЕЙ РЕАЛИЗАЦИИ
    @Autowired
    private GenerationID<TaskType> taskTypeGenerationID;

    /**
     * Метод создания типа задачи
     *
     * @param model создаваемый объект
     */
    @Override
    public void create(TaskType model) {
        //TODO сгенерировали и присвоили id
        model.setId(taskTypeGenerationID.generateId());

        repository.create(converter.toEntity(model));
    }

    /**
     * Метод получения типа задачи по идентификатору
     *
     * @param id идентификатор типа задачи
     * @return полученный объект тип задачи
     */
    @Override
    public TaskType get(Long id) {
        return converter.toVo(repository.get(id));
    }

    /**
     * Метод удаления типа задачи
     *
     * @param model удаляемый объект типа задачи
     */
    @Override
    public void remove(TaskType model) {
        if (checkingOtherDependenciesTaskType(model.getId()))
            throw new OperationIsNotPossibleException("Удаление невозможно, данный тип задачи используется!");
        repository.delete(model.getId());
    }

    private boolean checkingOtherDependenciesTaskType(Long id) {//TODO - при переходе на Hibernate подумать об оптимизации
        return getListService.getList().stream()
                .anyMatch(task -> task.getType().getId().equals(id));
    }

    /**
     * Метод обновления типа задачи
     *
     * @param model обновленный объект типа задачи
     */
    @Override
    public void refresh(TaskType model) {
        repository.update(converter.toEntity(model));
    }

    /**
     * Метод получения коллекции типов задачи по идентификатору проекта
     *
     * @param projectId идентификатор проекта, для которого получаем типы задач
     * @return коллекция типов задач
     */
    @Override
    public Collection<TaskType> getListByProjectId(Long projectId) {
        return byProjectId.getByProjectId(projectId).stream()
                .filter(entity -> entity.getProject_id().equals(projectId))
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }
}

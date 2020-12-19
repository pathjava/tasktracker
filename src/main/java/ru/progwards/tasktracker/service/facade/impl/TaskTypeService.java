package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.controller.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByProjectId;
import ru.progwards.tasktracker.repository.entity.TaskTypeEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.TaskType;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Бизнес-логика типов задачи
 *
 * @author Oleg Kiselev
 */
@Service
public class TaskTypeService implements CreateService<TaskType>, GetService<Long, TaskType>,
        RemoveService<TaskType>, RefreshService<TaskType>,
        GetListByProjectService<Long, TaskType>, GetListService<TaskType> {

    @Autowired
    private Repository<Long, TaskTypeEntity> repository;
    @Autowired
    private RepositoryByProjectId<Long, TaskTypeEntity> byProjectId;
    @Autowired
    private Converter<TaskTypeEntity, TaskType> converter;
    @Autowired
    private GetListService<Task> getListService;
    @Autowired
    private CopyService<WorkFlow> copyService;

    /**
     * Метод создания типа задачи
     *
     * @param model создаваемый объект
     */
    @Override
    public void create(TaskType model) {
        if (model.getWorkFlow().isPattern()) {
            WorkFlow copyWorkFlow = copyService.copy(
                    model.getWorkFlow(), getTemplateWorkFlow(model.getWorkFlow().getName(), model.getName())
            );
            model.setWorkFlow(copyWorkFlow);
        }

        TaskTypeEntity entity = converter.toEntity(model);
        repository.create(entity);
        model.setId(entity.getId());
    }

    /**
     * Метод создания шаблона WorkFlow для метода копирования
     *
     * @param workFlowName имя текущего WorkFlow
     * @param typeName     имя текущего TaskType
     * @return шаблон WorkFlow
     */
    public WorkFlow getTemplateWorkFlow(String workFlowName, String typeName) {
        return new WorkFlow(
                null,
                workFlowName + " - TaskType " + typeName,
                false,
                null,
                null,
                null
        );
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
     * Перед удалением выполняется проверка на доступность удаления типа задачи
     *
     * @param model удаляемый объект типа задачи
     */
    @Override
    public void remove(TaskType model) {
        if (checkingOtherDependenciesTaskType(model.getId()))
            throw new OperationIsNotPossibleException("Удаление невозможно, данный тип задачи используется!");
        repository.delete(model.getId());
    }

    /**
     * Метод проверки использования типа задачи другими ресурсами
     *
     * @param id идентификатор типа задачи
     * @return true - если удаляемый тип задачи где-то используется
     * и false - если тип задачи "свободный" и его можно удалять
     */
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
        if (model.getWorkFlow().isPattern()) {
            WorkFlow copyWorkFlow = copyService.copy(
                    model.getWorkFlow(), getTemplateWorkFlow(model.getWorkFlow().getName(), model.getName())
            );
            model.setWorkFlow(copyWorkFlow);
        }

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
                .filter(entity -> entity.getProject().getId().equals(projectId))
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }

    /**
     * Метод получения абсолютно всех типов задач
     *
     * @return коллекция типов задач
     */
    @Override
    public Collection<TaskType> getList() {
        return repository.get().stream()
                .map(entity -> converter.toVo(entity))
                .collect(Collectors.toList());
    }
}
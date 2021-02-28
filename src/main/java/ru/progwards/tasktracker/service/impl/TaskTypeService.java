package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Бизнес-логика типов задачи
 *
 * @author Oleg Kiselev
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskTypeService implements CreateService<TaskType>, GetService<Long, TaskType>,
        RemoveService<TaskType>, RefreshService<TaskType>, GetListService<TaskType>,
        TemplateService<TaskType>, Paging<Long, TaskType>, Sorting<Long, TaskType> {

    private final TaskTypeRepository taskTypeRepository;
    private final TaskRepository taskRepository;
    private final CopyService<WorkFlow> workFlowCopyService;

    /**
     * Метод создания типа задачи
     *
     * @param model создаваемый объект
     */
    @Transactional
    @Override
    public void create(TaskType model) {
        if (model.getWorkFlow() != null && model.getWorkFlow().getPattern()) {
            WorkFlow copyWorkFlow = workFlowCopyService.copy(
                    model.getWorkFlow(), getTemplateWorkFlow(model.getWorkFlow().getName(), model.getName())
            );
            model.setWorkFlow(copyWorkFlow);
        }
        taskTypeRepository.save(model);
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
        return taskTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("TaskType id=%s not found", id)));
    }

    /**
     * Метод удаления типа задачи
     * Перед удалением выполняется проверка на доступность удаления типа задачи
     *
     * @param model удаляемый объект типа задачи
     */
    @Transactional
    @Override
    public void remove(TaskType model) {
        if (checkingOtherDependenciesTaskType(model))
            throw new OperationIsNotPossibleException(
                    format("Удаление невозможно, TaskType id=%s используется!", model.getId())
            );
        taskTypeRepository.delete(model);
    }

    /**
     * Метод проверки использования типа задачи другими ресурсами
     *
     * @param model тип задачи
     * @return true - если удаляемый тип задачи где-то используется
     * и false - если тип задачи "свободный" и его можно удалять
     */
    private boolean checkingOtherDependenciesTaskType(TaskType model) {
        return taskRepository.existsTaskByType(model);
    }

    /**
     * Метод обновления типа задачи
     *
     * @param model обновленный объект типа задачи
     */
    @Transactional
    @Override
    public void refresh(TaskType model) {
        if (model.getWorkFlow() != null && model.getWorkFlow().getPattern()) {
            WorkFlow copyWorkFlow = workFlowCopyService.copy(
                    model.getWorkFlow(), getTemplateWorkFlow(model.getWorkFlow().getName(), model.getName())
            );
            model.setWorkFlow(copyWorkFlow);
        }
        taskTypeRepository.save(model);
    }

    /**
     * Метод получения листа всех типов задач (TaskType)
     *
     * @return лист типов задач
     */
    @Override
    public List<TaskType> getList() {
        return taskTypeRepository.findAll();
    }

    /**
     * Метод получения страницы пагинации всех типов задач (TaskType)
     *
     * @param pageable параметр/параметры по которым происходит выборка страницы пагинации объектов
     * @return страница пагинации (TaskType)
     */
    @Override
    public Page<TaskType> getPageableList(Pageable pageable) {
        return taskTypeRepository.findAll(pageable);
    }

    /**
     * Метод получения сортированного листа типов задач (TaskType)
     *
     * @param sort параметр/параметры, по которым происходит сортировка
     * @return сортированный лист (TaskType)
     */
    @Override
    public List<TaskType> getSortList(Sort sort) {
        return taskTypeRepository.findAll(sort);
    }

    CreateService<TaskType> taskTypeCreateService;

    /**
     * Метод создания TaskType по шаблону
     *
     * @param args [0] - Project, [1] - WorkFlow
     */
    @Transactional
    @Override
    public List<TaskType> createFromTemplate(Object... args) {
        if (args.length != 2)
            throw new OperationIsNotPossibleException("TaskType.createFromTemplate: 2 arguments expected");
        if (!(args[0] instanceof Project))
            throw new OperationIsNotPossibleException("TaskType.createFromTemplate: argument 0 must be Project");
        if (!(args[1] instanceof WorkFlow))
            throw new OperationIsNotPossibleException("TaskType.createFromTemplate: argument 1 must be WorkFlow");
        //if (!(args[2] instanceof List))
        //    throw new OperationIsNotPossibleException("TaskType.createFromTemplate: argument 2 must be List<String>");

        List<String> typeNames = List.of("Task", "Bug", "Epic");
        Project project = (Project) args[0];
        WorkFlow workflow = (WorkFlow) args[1];
        if (workflow.getPattern())
            workflow = workFlowCopyService.copy(
                    workflow, getTemplateWorkFlow(workflow.getName(), project.getName())
            );

        List<TaskType> resultList = new ArrayList<>(typeNames.size());
        for (String name : typeNames) {
            TaskType taskType = new TaskType();
            taskType.setName(name);
            taskType.setProject(project);
            taskType.setWorkFlow(workflow);
            taskTypeCreateService.create(taskType);
            resultList.add(taskType);
        }
        return resultList;
    }
}
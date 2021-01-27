package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.WorkFlowAction;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.repository.WorkFlowActionRepository;
import ru.progwards.tasktracker.service.*;

import java.util.Collection;
import java.util.List;

/**
 * Бизнес-логика типов задачи
 *
 * @author Aleksandr Sidelnikov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowActionService implements
        CreateService<WorkFlowAction>,
        GetService<Long, WorkFlowAction>,
        RemoveService<WorkFlowAction>,
        RefreshService<WorkFlowAction>,
        GetListService<WorkFlowAction>,
        GetListByParentService<Long, WorkFlowAction> {
//        TemplateService<WorkFlowAction> {

//    public class WorkFlowActionService implements
//    CreateService<WorkFlowAction>,
//    GetService<Long, WorkFlowAction>,
//    RemoveService<WorkFlowAction>,
//    RefreshService<WorkFlowAction>,
//    GetListService<WorkFlowAction>,
//    GetListByParentService<Long, WorkFlowAction> {

        private final WorkFlowActionRepository workFlowActionRepository;
    private final TaskRepository taskRepository;
    private final CopyService<WorkFlow> workFlowCopyService;

    /**
     * Метод создания типа задачи
     *
     * @param model создаваемый объект
     */
    @Transactional
    @Override
    public void create(WorkFlowAction model) {
//  sidnet1964
//        if (model.getWorkFlow() != null && model.getWorkFlow().getPattern()) {
//            WorkFlow copyWorkFlow = workFlowCopyService.copy(
//                    model.getWorkFlow(), getTemplateWorkFlow(model.getWorkFlow().getName(), model.getName())
//            );
//            model.setWorkFlow(copyWorkFlow);
//        }
        workFlowActionRepository.save(model);
    }

    /**
     * Метод создания шаблона WorkFlow для метода копирования
     *
     * @param workFlowName имя текущего WorkFlow
     * @param typeName     имя текущего WorkFlowAction
     * @return шаблон WorkFlow
     */
    public WorkFlow getTemplateWorkFlow(String workFlowName, String typeName) {
        return new WorkFlow(
                null,
                workFlowName + " - WorkFlowAction " + typeName,
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
    public WorkFlowAction get(Long id) {
        return workFlowActionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkFlowAction id=" + id + " not found"));
    }

    /**
     * Метод удаления типа задачи
     * Перед удалением выполняется проверка на доступность удаления типа задачи
     *
     * @param model удаляемый объект типа задачи
     */
    @Transactional
    @Override
    public void remove(WorkFlowAction model) {
//  sidnet1964
//        if (checkingOtherDependenciesWorkFlowAction(model))
//            throw new OperationIsNotPossibleException(
//                    "Удаление невозможно, WorkFlowAction id=" + model.getId() + " используется!"
//            );
        workFlowActionRepository.delete(model);
    }

    /**
     * Метод проверки использования типа задачи другими ресурсами
     *
     * @param model тип задачи
     * @return true - если удаляемый тип задачи где-то используется
     * и false - если тип задачи "свободный" и его можно удалять
     */
//  sidnet1964
//    private boolean checkingOtherDependenciesWorkFlowAction(WorkFlowAction model) {
//        return taskRepository.existsTaskByType(model);
//    }

    /**
     * Метод обновления типа задачи
     *
     * @param model обновленный объект типа задачи
     */
    @Transactional
    @Override
    public void refresh(WorkFlowAction model) {
//  sidnet1964
//        if (model.getWorkFlow() != null && model.getWorkFlow().getPattern()) {
//            WorkFlow copyWorkFlow = workFlowCopyService.copy(
//                    model.getWorkFlow(), getTemplateWorkFlow(model.getWorkFlow().getName(), model.getName())
//            );
//            model.setWorkFlow(copyWorkFlow);
//        }
        workFlowActionRepository.save(model);
    }

    /**
     * Метод получения абсолютно всех типов задач
     *
     * @return коллекция типов задач
     */
    @Override
    public List<WorkFlowAction> getList() {
        return workFlowActionRepository.findAll();
    }

//  sidnet1964  добавлено согласно списку интерфейсов исходного файла
//  что делать с перечеркнутым интерфейсом?

    @Override
    public Collection<WorkFlowAction> getListByParentId(Long parentId) {
        return null;
    }
}
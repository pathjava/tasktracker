package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.WorkFlowAction;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.repository.TaskRepository;
import ru.progwards.tasktracker.repository.WorkFlowActionRepository;
import ru.progwards.tasktracker.service.*;

import java.util.Collection;
import java.util.List;

/**
 * Бизнес-логика действий workflow
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

    private final WorkFlowActionRepository workFlowActionRepository;
    private final TaskRepository taskRepository;
    private final CopyService<WorkFlow> workFlowCopyService;

    /**
     * Метод создания действия workflow
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
     * Метод получения действия workflow по идентификатору
     *
     * @param id идентификатор действия workflow
     * @return полученный объект действия workflow
     */
    @Override
    public WorkFlowAction get(Long id) {
        return workFlowActionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkFlowAction id=" + id + " not found"));
    }

    /**
     * Метод удаления действия workflow
     * Перед удалением выполняется проверка на доступность удаления действия workflow
     *
     * @param model удаляемый объект действия workflow
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
     * Метод обновления действия workflow
     *
     * @param model обновленный объект действия workflow
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
     * Метод получения абсолютно всех действий workflow
     *
     * @return коллекция действия workflow
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
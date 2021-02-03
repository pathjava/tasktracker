package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.model.WorkFlowStatus;
import ru.progwards.tasktracker.repository.WorkFlowRepository;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Бизнес-логика работы с воркфлоу
 *
 * @author Gregory Lobkov
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class WorkFlowService implements CreateService<WorkFlow>, RemoveService<WorkFlow>, GetService<Long, WorkFlow>, RefreshService<WorkFlow>, GetListService<WorkFlow>, CopyService<WorkFlow> {

    private final WorkFlowRepository repository;
    private final CopyService<WorkFlowStatus> statusCopyService;


    /**
     * Создание нового Workflow
     *
     * @param workFlow новый Workflow
     */
    @Override
    public void create(WorkFlow workFlow) {
        repository.save(workFlow);
    }


    /**
     * Удаление Workflow
     *
     * @param workFlow удаляемый Workflow
     */
    @Transactional
    @Override
    public void remove(WorkFlow workFlow) {
        repository.delete(workFlow);
    }


    /**
     * Получить информацию по Workflow
     *
     * @param id идентификатор Workflow
     * @return Workflow
     */
    @Override
    public WorkFlow get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("WorkFlow id=" + id + " not found"));
    }


    /**
     * Обновить поля Workflow
     *
     * @param workFlow измененный Workflow
     */
    @Transactional
    @Override
    public void refresh(WorkFlow workFlow) {
        if(workFlow.getPattern()) {
            throw new OperationIsNotPossibleException("Workflow is a pattern. Can't update");
        }
        repository.save(workFlow);
    }

    /**
     * Получить список всех Workflow
     *
     * @return список Workflow
     */
    @Override
    public List<WorkFlow> getList() {
        return repository.findAll();
    }

    /**
     * Создать копию Workflow
     * Если задан шаблон, то свойства в скопированном объекте заменятся на не null свойства объекта
     *
     * @param modelFrom бизнес объект, который необходимо скопировать
     * @param template  шаблон для замены свойств во вновь созданном объекте
     * @return копия объекта {@code model}
     */
    @Override
    public WorkFlow copy(WorkFlow modelFrom, WorkFlow template) {
        WorkFlow clone = new WorkFlow();
        clone.setName(template.getName() == null ? modelFrom.getName() : template.getName());
        clone.setPattern(template.getPattern() == null ? modelFrom.getPattern() : template.getPattern());
        clone.setTaskTypes(template.getTaskTypes() == null ? modelFrom.getTaskTypes() : template.getTaskTypes());
        if(template.getStatuses()!=null) {
            clone.setStatuses(template.getStatuses());
            clone.setStartStatus(template.getStartStatus());
        } else {
            WorkFlowStatus statusTemplate = new WorkFlowStatus();
            statusTemplate.setTasks(new ArrayList<>(0));
            statusTemplate.setWorkflow(clone);
            List<WorkFlowStatus> statuses = new ArrayList<>(modelFrom.getStatuses().size());
            for(WorkFlowStatus s: modelFrom.getStatuses()) {
                WorkFlowStatus sc = statusCopyService.copy(s, statusTemplate);
                if(modelFrom.getStartStatus() == s) {
                    clone.setStartStatus(sc);
                }
                statuses.add(sc);
            }
            clone.setStatuses(statuses);
        }

        return clone;
    }

}
package ru.progwards.tasktracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.RepositoryByParentId;
import ru.progwards.tasktracker.repository.deprecated.entity.WorkFlowActionEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.WorkFlowAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Бизнес-логика работы над действиями Workflow
 *
 * @author Gregory Lobkov
 */
@Service
public class WorkFlowActionService implements CreateService<WorkFlowAction>, RemoveService<WorkFlowAction>, GetListService<WorkFlowAction>, GetService<Long, WorkFlowAction>, RefreshService<WorkFlowAction>, GetListByParentService<Long, WorkFlowAction> {

    @Autowired
    private Repository<Long, WorkFlowActionEntity> workFlowActionRepository;
    @Autowired
    private Converter<WorkFlowActionEntity, WorkFlowAction> workFlowActionConverter;
    @Autowired
    private RepositoryByParentId<Long, WorkFlowActionEntity> workFlowActionEntityRepositoryByParentId;


    /**
     * Создание нового WorkFlowAction
     *
     * @param workFlowAction новый WorkFlowAction
     */
    @Override
    public void create(WorkFlowAction workFlowAction) {
        WorkFlowActionEntity entity = workFlowActionConverter.toEntity(workFlowAction);
        workFlowActionRepository.create(entity);
        workFlowAction.setId(entity.getId());
    }


    /**
     * Удаление WorkFlowAction
     *
     * @param workFlowAction удаляемый WorkFlowAction
     */
    @Override
    public void remove(WorkFlowAction workFlowAction) {
        workFlowActionRepository.delete(workFlowAction.getId());
    }


    /**
     * Получить информацию по WorkFlowAction
     *
     * @param id идентификатор WorkFlowAction
     * @return WorkFlowAction
     */
    @Override
    public WorkFlowAction get(Long id) {
        return workFlowActionConverter.toVo(workFlowActionRepository.get(id));
    }


    /**
     * Обновить поля WorkFlowAction
     *
     * @param workFlowAction измененный WorkFlowAction
     */
    @Override
    public void refresh(WorkFlowAction workFlowAction) {
        workFlowActionRepository.update(workFlowActionConverter.toEntity(workFlowAction));
    }


    /**
     * Получить список действий для определенного статуса
     * 
     * @param parentId WorkFlowStatus.id
     * @return список действий
     */
    @Override
    public Collection<WorkFlowAction> getListByParentId(Long parentId) {
        // получили список сущностей
        Collection<WorkFlowActionEntity> workFlowActionEntities = workFlowActionEntityRepositoryByParentId.getByParentId(parentId);
        List<WorkFlowAction> workFlowActions = new ArrayList<>(workFlowActionEntities.size());
        // преобразуем к бизнес-объектам
        for (WorkFlowActionEntity entity:workFlowActionEntities) {
            workFlowActions.add(workFlowActionConverter.toVo(entity));
        }
        return workFlowActions;
    }

    /**
     * Получить список всех действий
     *
     * @return
     */
    @Override
    public Collection<WorkFlowAction> getList() {
        // получили список сущностей
        Collection<WorkFlowActionEntity> workFlowActionEntities = workFlowActionRepository.get();
        List<WorkFlowAction> workFlowActions = new ArrayList<>(workFlowActionEntities.size());
        // преобразуем к бизнес-объектам
        for (WorkFlowActionEntity entity:workFlowActionEntities) {
            workFlowActions.add(workFlowActionConverter.toVo(entity));
        }
        return workFlowActions;
    }

}

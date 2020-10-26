package ru.progwards.tasktracker.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByParentId;
import ru.progwards.tasktracker.repository.entity.WorkFlowActionEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkFlowAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Бизнес-логика работы над действиями Workflow
 *
 * @author Gregory Lobkov
 */
@Service
public class WorkFlowActionService implements CreateService<WorkFlowAction>, RemoveService<WorkFlowAction>, GetService<Long, WorkFlowAction>, RefreshService<WorkFlowAction>, GetListByParentService<Long, WorkFlowAction> {

    @Autowired
    private Repository<Long, WorkFlowActionEntity> workFlowActionRepository;
    @Autowired
    private Converter<WorkFlowActionEntity, WorkFlowAction> workFlowActionConverter;
    @Autowired
    private RepositoryByParentId<Long, WorkFlowActionEntity> workFlowActionEntityRepositoryByParentId;


    /**
     * Создание нового WorkFlowAction
     *
     * @param WorkFlowAction новый WorkFlowAction
     */
    @Override
    public void create(WorkFlowAction WorkFlowAction) {
        workFlowActionRepository.create(workFlowActionConverter.toEntity(WorkFlowAction));
    }


    /**
     * Удаление WorkFlowAction
     *
     * @param WorkFlowAction удаляемый WorkFlowAction
     */
    @Override
    public void remove(WorkFlowAction WorkFlowAction) {
        workFlowActionRepository.delete(WorkFlowAction.getId());
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
     * @param WorkFlowAction измененный WorkFlowAction
     */
    @Override
    public void refresh(WorkFlowAction WorkFlowAction) {
        workFlowActionRepository.update(workFlowActionConverter.toEntity(WorkFlowAction));
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

}

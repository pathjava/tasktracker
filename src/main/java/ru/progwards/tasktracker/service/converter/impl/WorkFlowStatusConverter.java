package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.WorkFlowStatusEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListByParentService;
import ru.progwards.tasktracker.service.vo.WorkFlowAction;
import ru.progwards.tasktracker.service.vo.WorkFlowState;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;

import java.util.List;


/**
 * Преобразование valueObject <-> entity
 *
 * WorkFlowStatus <-> WorkFlowStatusEntity
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowStatusConverter implements Converter<WorkFlowStatusEntity, WorkFlowStatus> {

    /**
     * Сервис получения списка действий по статусу
     */
    @Autowired
    private GetListByParentService<Long, WorkFlowAction> workFlowActionGetListByParentService;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param entity сущность репозитория
     * @return бизнес-объект
     */
    @Override
    public WorkFlowStatus toVo(WorkFlowStatusEntity entity) {
        List<WorkFlowAction> listByParentId = (List)workFlowActionGetListByParentService.getListByParentId(entity.getId()); // должно стать lazy load в будущем
        return new WorkFlowStatus(entity.getId(), entity.getWorkflow_id(), entity.getName(),
                WorkFlowState.valueOf(entity.getState()), listByParentId, entity.getAlwaysAllow());
    }


    /**
     * Преобразовать в сущность хранилища
     *
     * @param vo бизнес-объект
     * @return сущность репозитория
     */
    @Override
    public WorkFlowStatusEntity toEntity(WorkFlowStatus vo) {
        return new WorkFlowStatusEntity(vo.getId(), vo.getWorkflow_id(), vo.getName(), vo.getState().toString(), vo.getAllowFromAnyStatus());
    }

}
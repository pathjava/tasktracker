package ru.progwards.tasktracker.repository.deprecated.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.deprecated.entity.WorkFlowActionEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.WorkFlowAction;
import ru.progwards.tasktracker.model.WorkFlowStatus;


/**
 * Преобразование valueObject <-> entity
 *
 * WorkFlowAction <-> WorkFlowActionEntity
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowActionConverter implements Converter<WorkFlowActionEntity, WorkFlowAction> {

    /**
     * Сервис получения татусов WorkFlow
     */
    @Autowired
    private GetService<Long, WorkFlowStatus> workFlowStatusGetService;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param entity сущность репозитория
     * @return бизнес-объект
     */
    @Override
    public WorkFlowAction toVo(WorkFlowActionEntity entity) {
//        WorkFlowStatus workFlowStatus = workFlowStatusGetService.get(entity.getNext_status_id()); // должно стать lazy load в будущем
//        return new WorkFlowAction(entity.getId(), entity.getParentStatus_id(), entity.getName(),
//                entity.getNext_status_id(), workFlowStatus);
        return null;
    }


    /**
     * Преобразовать в сущность хранилища
     *
     * @param vo бизнес-объект
     * @return сущность репозитория
     */
    @Override
    public WorkFlowActionEntity toEntity(WorkFlowAction vo) {
//        return new WorkFlowActionEntity(vo.getId(), vo.getParentStatus_id(), vo.getName(), vo.getStatus_id());
        return null;
    }

}
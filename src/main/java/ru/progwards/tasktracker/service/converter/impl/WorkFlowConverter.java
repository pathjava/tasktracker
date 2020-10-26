package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.WorkFlowEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.WorkFlow;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;


/**
 * Преобразование valueObject <-> entity
 *
 * WorkFlow <-> WorkFlowEntity
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowConverter implements Converter<WorkFlowEntity, WorkFlow> {

    /**
     * Сервис получения статусов Workflow
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
    public WorkFlow toVo(WorkFlowEntity entity) {
        WorkFlowStatus workFlowStatus = workFlowStatusGetService.get(entity.getStart_status_id()); // должно стать lazy load в будущем
        return new WorkFlow(entity.getId(), entity.getName(), entity.isPattern(),
                entity.getStart_status_id(), workFlowStatus);
    }


    /**
     * Преобразовать в сущность хранилища
     *
     * @param vo бизнес-объект
     * @return сущность репозитория
     */
    @Override
    public WorkFlowEntity toEntity(WorkFlow vo) {
        return new WorkFlowEntity(vo.getId(), vo.getName(), vo.isPattern(), vo.getStart_status_id());
    }

}
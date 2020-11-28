package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkFlowStatusDtoFull;
import ru.progwards.tasktracker.service.facade.GetListByParentService;
import ru.progwards.tasktracker.service.vo.WorkFlowAction;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * Преобразование valueObject <-> dto
 *
 * WorkFlowStatus <-> WorkFlowStatusDtoFull
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowStatusDtoFullConverter implements Converter<WorkFlowStatus, WorkFlowStatusDtoFull> {

    /**
     * Сервис получения списка действий по статусу
     */
    @Autowired
    private GetListByParentService<Long, WorkFlowAction> workFlowActionGetListByParentService;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public WorkFlowStatus toModel(WorkFlowStatusDtoFull dto) {
        List<WorkFlowAction> listByParentId = (List)workFlowActionGetListByParentService.getListByParentId(dto.getId()); // должно стать lazy load в будущем
        return new WorkFlowStatus(dto.getId(), dto.getWorkflow_id(), dto.getName(),
                dto.getState(), listByParentId, dto.getAlwaysAllow());
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowStatusDtoFull toDto(WorkFlowStatus model) {
        return new WorkFlowStatusDtoFull(model.getId(), model.getWorkflow_id(), model.getName(), model.getState(), model.getAlwaysAllow(), new ArrayList<WorkFlowAction>());
    }

}
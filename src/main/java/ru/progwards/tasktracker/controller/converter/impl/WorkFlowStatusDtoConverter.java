package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkFlowStatusDto;
import ru.progwards.tasktracker.service.facade.GetListByParentService;
import ru.progwards.tasktracker.service.vo.WorkFlowAction;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;

import java.util.List;


/**
 * Преобразование valueObject <-> dto
 *
 * WorkFlowStatus <-> WorkFlowStatusDto
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowStatusDtoConverter implements Converter<WorkFlowStatus, WorkFlowStatusDto> {

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
    public WorkFlowStatus toModel(WorkFlowStatusDto dto) {
        List<WorkFlowAction> listByParentId = (List)workFlowActionGetListByParentService.getListByParentId(dto.getId()); // должно стать lazy load в будущем
        return new WorkFlowStatus(dto.getId(), dto.getWorkflow_id(), dto.getName(),
                dto.getState(), listByParentId, dto.getAllowFromAnyStatus());
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowStatusDto toDto(WorkFlowStatus model) {
        return new WorkFlowStatusDto(model.getId(), model.getWorkflow_id(), model.getName(), model.getState(), model.getAllowFromAnyStatus());
    }

}
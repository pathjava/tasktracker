package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.dto.WorkFlowActionDto;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.WorkFlowAction;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;


/**
 * Преобразование valueObject <-> dto
 *
 * WorkFlowAction <-> WorkFlowActionDto
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowActionDtoConverter implements Converter<WorkFlowAction, WorkFlowActionDto> {

    /**
     * Сервис получения татусов WorkFlow
     */
    @Autowired
    private GetService<Long, WorkFlowStatus> workFlowStatusGetService;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public WorkFlowAction toModel(WorkFlowActionDto dto) {
        WorkFlowStatus workFlowStatus = workFlowStatusGetService.get(dto.getStatus_id()); // должно стать lazy load в будущем
        return new WorkFlowAction(dto.getId(), dto.getParentStatus_id(), dto.getName(),
                dto.getStatus_id(), workFlowStatus);
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowActionDto toDto(WorkFlowAction model) {
        return new WorkFlowActionDto(model.getId(), model.getParentStatus_id(), model.getName(), model.getStatus_id());
    }

}
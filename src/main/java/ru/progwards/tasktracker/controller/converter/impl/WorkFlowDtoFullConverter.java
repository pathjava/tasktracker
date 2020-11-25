package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.dto.WorkFlowDtoFull;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.WorkFlow;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;


/**
 * Преобразование valueObject <-> dto
 *
 * WorkFlow <-> WorkFlowDtoFull
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowDtoFullConverter implements Converter<WorkFlow, WorkFlowDtoFull> {

    /**
     * Сервис получения статусов Workflow
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
    public WorkFlow toModel(WorkFlowDtoFull dto) {
        WorkFlowStatus workFlowStatus = workFlowStatusGetService.get(dto.getStart_status_id()); // должно стать lazy load в будущем
        return new WorkFlow(dto.getId(), dto.getName(), dto.isPattern(),
                dto.getStart_status_id(), workFlowStatus);
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowDtoFull toDto(WorkFlow model) {
        return new WorkFlowDtoFull(model.getId(), model.getName(), model.isPattern(), model.getStart_status_id());
    }

}
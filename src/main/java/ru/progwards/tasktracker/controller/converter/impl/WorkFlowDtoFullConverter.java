package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.dto.WorkFlowDtoFull;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetListByParentService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.WorkFlow;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;

import java.util.ArrayList;
import java.util.List;


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
    private GetService<Long, WorkFlowStatus> statusGetService;

    /**
     * Сервис получения статусов Workflow по бизнес-процессу
     */
    @Autowired
    private GetListByParentService<Long, WorkFlowStatus> statusGetListByParentService;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public WorkFlow toModel(WorkFlowDtoFull dto) {
        WorkFlowStatus startStatus = statusGetService.get(dto.getStart_status_id()); // должно стать lazy load в будущем
        List<WorkFlowStatus> statuses = new ArrayList(statusGetListByParentService.getListByParentId(dto.getId()));
        return new WorkFlow(dto.getId(), dto.getName(), dto.isPattern(),
                dto.getStart_status_id(), startStatus, statuses);
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
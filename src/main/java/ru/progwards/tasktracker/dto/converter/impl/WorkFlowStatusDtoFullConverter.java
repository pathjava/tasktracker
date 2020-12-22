package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoFull;
import ru.progwards.tasktracker.service.GetListByParentService;
import ru.progwards.tasktracker.model.WorkFlowAction;
import ru.progwards.tasktracker.model.WorkFlowStatus;


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
//        List<WorkFlowAction> listByParentId = (List)workFlowActionGetListByParentService.getListByParentId(dto.getId()); // должно стать lazy load в будущем
//        return new WorkFlowStatus(dto.getId(), dto.getWorkflow_id(), dto.getName(),
//                dto.getState(), listByParentId, dto.getAlwaysAllow()
//                , null); //TODO не NULL, а что?
        return null;
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowStatusDtoFull toDto(WorkFlowStatus model) {
//        return new WorkFlowStatusDtoFull(model.getId(), model.getWorkflow_id(), model.getName(), model.getState(), model.getAlwaysAllow(), new ArrayList<WorkFlowAction>());
        return null;
    }

}
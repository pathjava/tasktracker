package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.dto.WorkFlowActionDtoFull;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.service.GetService;

/**
 * Преобразование valueObject <-> dto
 *
 * WorkFlowAction <-> WorkFlowActionDtoFull
 *
 * @author Aleksandr Sidelnikov
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowActionDtoFullConverter implements Converter<WorkFlowAction, WorkFlowActionDtoFull> {

    private final GetService<Long, WorkFlowAction> workFlowActionGetService;
    private final GetService<Long, WorkFlowStatus> workFlowStatusGetService;

    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public WorkFlowAction toModel(WorkFlowActionDtoFull dto) {
        if (dto == null)
            return null;
        else if (dto.getId() == null) {
            return new WorkFlowAction(
                    null,
                    checkCounterStatusDto(dto.getParentStatus_id()),
                    dto.getName().trim(),
                    checkCounterStatusDto(dto.getNextStatus_id()));
        } else {
            WorkFlowAction workFlowAction = workFlowActionGetService.get(dto.getId());
            workFlowAction.setId(dto.getId());
            workFlowAction.setParentStatus(workFlowStatusGetService.get(dto.getParentStatus_id()));
            workFlowAction.setName(dto.getName().trim());
            workFlowAction.setNextStatus(workFlowStatusGetService.get(dto.getNextStatus_id()));
            return workFlowAction;
        }
    }

    /**
     * Метод проверки существования встречного типа отношения
     *
     * @param id идентификатор WorkFlowStatus
     * @return WorkFlowStatus или null
     */
    private WorkFlowStatus checkCounterStatusDto(Long id) {
        return id != null ? workFlowStatusGetService.get(id) : null;
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowActionDtoFull toDto(WorkFlowAction model) {
        if (model == null)
            return null;
        else
            return new WorkFlowActionDtoFull(
                    model.getId(),
                    model.getParentStatus().getId(),
                    model.getName(),
                    model.getNextStatus().getId()
            );
    }

}
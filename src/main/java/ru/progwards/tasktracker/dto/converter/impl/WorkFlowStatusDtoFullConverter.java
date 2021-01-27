package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.WorkFlowActionDtoPreview;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.dto.WorkFlowStateDtoPreview;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.model.WorkFlowAction;
import ru.progwards.tasktracker.model.WorkFlowStatus;
import ru.progwards.tasktracker.model.types.WorkFlowState;
import ru.progwards.tasktracker.service.GetService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Преобразование valueObject <-> dto
 *
 * WorkFlowStatus <-> WorkFlowStatusDtoFull
 *
 * @author Gregory Lobkov
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowStatusDtoFullConverter implements Converter<WorkFlowStatus, WorkFlowStatusDtoFull> {


    private final GetService<Long, WorkFlowStatus> getService;
    private final Converter<WorkFlowState, WorkFlowStateDtoPreview> stateDtoConverter;
    private final Converter<WorkFlow, WorkFlowDtoPreview> workflowDtoConverter;
    private final Converter<WorkFlowAction, WorkFlowActionDtoPreview> actionDtoConverter;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public WorkFlowStatus toModel(WorkFlowStatusDtoFull dto) {
        WorkFlowStatus model = null;
        if (dto != null) {
            WorkFlow workflow = workflowDtoConverter.toModel(dto.getWorkflow());
            WorkFlowState state = stateDtoConverter.toModel(dto.getState());
            if (dto.getId() == null) {
                model = new WorkFlowStatus(
                        null,
                        workflow,
                        dto.getName(),
                        state,
                        Collections.emptyList(),
                        dto.getAlwaysAllow(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList()
                );
            } else {
                model = getService.get(dto.getId());
                model.setName(dto.getName());
                model.setState(state);
                model.setAlwaysAllow(dto.getAlwaysAllow());
            }
        }
        return model;
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public WorkFlowStatusDtoFull toDto(WorkFlowStatus model) {
        WorkFlowStatusDtoFull dto = null;
        if (model != null) {
            dto = new WorkFlowStatusDtoFull(
                    null,
                    workflowDtoConverter.toDto(model.getWorkflow()),
                    model.getName(),
                    stateDtoConverter.toDto(model.getState()),
                    getDtoFromModelActions(model.getActions()),
                    model.getAlwaysAllow()
            );
        }
        return dto;
    }


    /**
     * Получить из списка действий vo список действий dto
     *
     * @param actions список действий vo
     * @return список действий dto
     */
    private List<WorkFlowActionDtoPreview> getDtoFromModelActions(List<WorkFlowAction> actions) {
        ArrayList<WorkFlowActionDtoPreview> result = new ArrayList<>(actions.size());
        for (WorkFlowAction a:actions)
            result.add(actionDtoConverter.toDto(a));
        return result;
    }

}
package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.dto.WorkFlowStateDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoPreview;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.model.WorkFlowStatus;
import ru.progwards.tasktracker.model.types.WorkFlowState;
import ru.progwards.tasktracker.service.GetService;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class WorkFlowStatusDtoPreviewConverter implements Converter<WorkFlowStatus, WorkFlowStatusDtoPreview> {

    private final GetService<Long, WorkFlowStatus> getService;
    private final Converter<WorkFlowState, WorkFlowStateDtoPreview> stateDtoConverter;

    @Override
    public WorkFlowStatus toModel(WorkFlowStatusDtoPreview dto) {
        WorkFlowStatus model = null;
        if (dto != null) {
            model = getService.get(dto.getId());
        }
        return model;
    }

    @Override
    public WorkFlowStatusDtoPreview toDto(WorkFlowStatus model) {
        WorkFlowStatusDtoPreview dto = null;
        if (model != null) {
            dto = new WorkFlowStatusDtoPreview(
                    model.getId(),
                    model.getName(),
                    stateDtoConverter.toDto(model.getState())
            );
        }
        return dto;
    }
}

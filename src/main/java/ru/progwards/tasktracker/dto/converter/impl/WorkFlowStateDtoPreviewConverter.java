package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.WorkFlowStateDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.types.WorkFlowState;

/**
 * Преобразование ENUM valueObject <-> dto
 *
 * WorkFlowState <-> WorkFlowStateDtoPreview
 *
 * @author Gregory Lobkov
 */
@Component
public class WorkFlowStateDtoPreviewConverter implements Converter<WorkFlowState, WorkFlowStateDtoPreview> {

    @Override
    public WorkFlowState toModel(WorkFlowStateDtoPreview dto) {
        return dto == null ? null : WorkFlowState.valueOf(dto.getName());
    }

    @Override
    public WorkFlowStateDtoPreview toDto(WorkFlowState model) {
        return new WorkFlowStateDtoPreview(model.toString());
    }
}
package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoPreview;
import ru.progwards.tasktracker.model.WorkFlowStatus;

@Component
public class WorkFlowStatusDtoPreviewConverter implements Converter<WorkFlowStatus, WorkFlowStatusDtoPreview> {
    @Override
    public WorkFlowStatus toModel(WorkFlowStatusDtoPreview dto) {
        return null;
    }

    @Override
    public WorkFlowStatusDtoPreview toDto(WorkFlowStatus model) {
        return null;
    }
}

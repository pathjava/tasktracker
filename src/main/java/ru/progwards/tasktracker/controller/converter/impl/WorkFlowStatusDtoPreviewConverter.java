package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkFlowStatusDtoPreview;
import ru.progwards.tasktracker.service.vo.WorkFlowStatus;

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

package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.model.WorkFlow;

@Component
public class WorkFlowDtoPreviewConverter implements Converter<WorkFlow, WorkFlowDtoPreview> {
    @Override
    public WorkFlow toModel(WorkFlowDtoPreview dto) {
        return null;
    }

    @Override
    public WorkFlowDtoPreview toDto(WorkFlow model) {
        return null;
    }
}

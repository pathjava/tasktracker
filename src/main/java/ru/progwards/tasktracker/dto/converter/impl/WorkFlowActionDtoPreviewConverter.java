package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.WorkFlowActionDtoPreview;
import ru.progwards.tasktracker.model.WorkFlowAction;

@Component
public class WorkFlowActionDtoPreviewConverter implements Converter<WorkFlowAction, WorkFlowActionDtoPreview> {
    @Override
    public WorkFlowAction toModel(WorkFlowActionDtoPreview dto) {
        return null;
    }

    @Override
    public WorkFlowActionDtoPreview toDto(WorkFlowAction model) {
        return null;
    }
}

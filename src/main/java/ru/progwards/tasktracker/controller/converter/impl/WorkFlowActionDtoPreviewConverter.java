package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkFlowActionDtoPreview;
import ru.progwards.tasktracker.service.vo.WorkFlowAction;

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

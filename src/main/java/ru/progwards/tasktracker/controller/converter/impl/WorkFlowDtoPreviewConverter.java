package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.service.vo.WorkFlow;

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

package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkLogDtoPreview;
import ru.progwards.tasktracker.service.vo.WorkLog;

/**
 * @author Oleg Kiselev
 */
@Component
public class WorkLogDtoPreviewConverter implements Converter<WorkLog, WorkLogDtoPreview> {
    @Override
    public WorkLog toModel(WorkLogDtoPreview dto) {
        return null;
    }

    @Override
    public WorkLogDtoPreview toDto(WorkLog model) {
        return null;
    }
}

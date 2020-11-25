package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskAttachmentDtoPreview;
import ru.progwards.tasktracker.service.vo.TaskAttachment;

@Component
public class TaskAttachmentDtoPreviewConverter implements Converter<TaskAttachment, TaskAttachmentDtoPreview> {
    @Override
    public TaskAttachment toModel(TaskAttachmentDtoPreview dto) {
        return null;
    }

    @Override
    public TaskAttachmentDtoPreview toDto(TaskAttachment model) {
        return null;
    }
}

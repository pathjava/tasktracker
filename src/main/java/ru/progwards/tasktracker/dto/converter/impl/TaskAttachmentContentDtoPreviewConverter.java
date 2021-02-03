package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.TaskAttachmentContentDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.service.GetService;

import java.util.Collections;


/**
 * Преобразование valueObject <-> dto
 *
 * AttachmentContent <-> AttachmentContentDtoFull
 *
 * @author Gregory Lobkov
 */
@Component
public class TaskAttachmentContentDtoPreviewConverter implements Converter<TaskAttachmentContent, TaskAttachmentContentDtoPreview> {

    @Autowired
    private GetService<Long, TaskAttachmentContent> getService;

    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public TaskAttachmentContent toModel(TaskAttachmentContentDtoPreview dto) {
        TaskAttachmentContent model = null;
        if (dto != null) {
            model = getService.get(dto.getId());
        }
        return model;
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public TaskAttachmentContentDtoPreview toDto(TaskAttachmentContent model) {
        TaskAttachmentContentDtoPreview dto = null;
        if (model != null) {
            dto = new TaskAttachmentContentDtoPreview(
                    model.getId()
            );
        }
        return dto;
    }

}

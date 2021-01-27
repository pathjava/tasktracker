package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.TaskAttachmentContentDtoFull;
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
public class TaskAttachmentContentDtoFullConverter implements Converter<TaskAttachmentContent, TaskAttachmentContentDtoFull> {

    @Autowired
    private GetService<Long, TaskAttachmentContent> getService;

    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public TaskAttachmentContent toModel(TaskAttachmentContentDtoFull dto) {
        TaskAttachmentContent model = null;
        if (dto != null)
            if (dto.getId() == null) {
                model = new TaskAttachmentContent(
                        null,
                        dto.getData(),
                        Collections.emptyList()
                );
            } else {
                model = getService.get(dto.getId());
                model.setData(dto.getData());
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
    public TaskAttachmentContentDtoFull toDto(TaskAttachmentContent model) {
        TaskAttachmentContentDtoFull dto = null;
        if (model != null)
            dto = new TaskAttachmentContentDtoFull(
                    model.getId(),
                    model.getData()
            );
        return dto;
    }

}

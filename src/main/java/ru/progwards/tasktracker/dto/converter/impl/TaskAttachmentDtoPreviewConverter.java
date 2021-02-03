package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.progwards.tasktracker.dto.TaskAttachmentDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.service.GetService;


/**
 * Преобразование valueObject <-> dto
 *
 * TaskAttachment <-> TaskAttachmentDtoFull
 *
 * @author Gregory Lobkov
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskAttachmentDtoPreviewConverter implements Converter<TaskAttachment, TaskAttachmentDtoPreview> {

    private final GetService<Long, TaskAttachment> getService;

    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public TaskAttachment toModel(TaskAttachmentDtoPreview dto) {
        TaskAttachment model = null;
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
    public TaskAttachmentDtoPreview toDto(TaskAttachment model) {
        TaskAttachmentDtoPreview dto = null;
        if (model != null) {
            dto = new TaskAttachmentDtoPreview(
                    model.getId(), model.getName(),
                    model.getExtension(), model.getSize(), model.getCreated()
            );
        }
        return dto;
    }

}

package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskAttachmentDtoPreview;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.model.TaskAttachment;


/**
 * Преобразование valueObject <-> dto
 *
 * TaskAttachment <-> TaskAttachmentDtoFull
 *
 * @author Gregory Lobkov
 */
@Controller
public class TaskAttachmentDtoPreviewConverter implements Converter<TaskAttachment, TaskAttachmentDtoPreview> {


    /**
     * Сервис получения содержимого файла
     */
    @Autowired
    private GetService<Long, TaskAttachmentContent> attachmentContentGetService;

    /**
     * Сервис получения связи задача - вложение
     */
    @Autowired
    private GetService<Long, TaskAttachment> taskAttachmentGetService;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public TaskAttachment toModel(TaskAttachmentDtoPreview dto) {
        TaskAttachment saved = taskAttachmentGetService.get(dto.getId());
        TaskAttachmentContent taskAttachmentContent = attachmentContentGetService.get(saved.getContent().getId());

        return new TaskAttachment(dto.getId(), saved.getTask(), dto.getName(), dto.getExtension(),
                dto.getSize(), dto.getCreated(), taskAttachmentContent);
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public TaskAttachmentDtoPreview toDto(TaskAttachment model) {
        return new TaskAttachmentDtoPreview(model.getId(), model.getName(), model.getExtension(),
                model.getSize(), model.getCreated());
    }

}
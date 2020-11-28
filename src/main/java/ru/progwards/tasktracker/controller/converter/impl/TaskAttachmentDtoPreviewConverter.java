package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskAttachmentDtoFull;
import ru.progwards.tasktracker.controller.dto.TaskAttachmentDtoPreview;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.AttachmentContent;
import ru.progwards.tasktracker.service.vo.TaskAttachment;


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
    private GetService<Long, AttachmentContent> attachmentContentGetService;

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
        AttachmentContent attachmentContent = attachmentContentGetService.get(saved.getContentId());

        return new TaskAttachment(dto.getId(), saved.getTaskId(), dto.getName(), dto.getExtension(),
                dto.getSize(), dto.getCreated(), saved.getContentId(), attachmentContent);
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

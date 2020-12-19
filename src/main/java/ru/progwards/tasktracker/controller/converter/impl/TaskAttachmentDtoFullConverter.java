package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskAttachmentDtoFull;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.TaskAttachmentContent;
import ru.progwards.tasktracker.service.vo.TaskAttachment;


/**
 * Преобразование valueObject <-> dto
 *
 * TaskAttachment <-> TaskAttachmentDtoFull
 *
 * @author Gregory Lobkov
 */
@Controller
public class TaskAttachmentDtoFullConverter implements Converter<TaskAttachment, TaskAttachmentDtoFull> {


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
    public TaskAttachment toModel(TaskAttachmentDtoFull dto) {
//        TaskAttachment saved = taskAttachmentGetService.get(dto.getId());
//        AttachmentContent attachmentContent = attachmentContentGetService.get(dto.getContentId());
//
//        return new TaskAttachment(dto.getId(), dto.getTaskId(), dto.getName(), dto.getExtension(),
//                dto.getSize(), dto.getCreated(), saved.getContentId(), attachmentContent);
        return null;
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public TaskAttachmentDtoFull toDto(TaskAttachment model) {
//        return new TaskAttachmentDtoFull(model.getId(), model.getTask(), model.getName(), model.getExtension(),
//                model.getSize(), model.getCreated());
        return null;
    }

}

package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskAttachmentDto;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.AttachmentContent;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.TaskAttachment;


/**
 * Преобразование valueObject <-> dto
 *
 * TaskAttachment <-> TaskAttachmentDto
 *
 * @author Gregory Lobkov
 */
@Controller
public class TaskAttachmentDtoConverter implements Converter<TaskAttachment, TaskAttachmentDto> {


    /**
     * Сервис получения содержимого файла
     */
    @Autowired
    private GetService<Long, AttachmentContent> attachmentContentGetService;


    /**
     * Сервис получения задачи
     */
    @Autowired
    private GetService<Long, Task> taskGetService;


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public TaskAttachment toModel(TaskAttachmentDto dto) {
        String fileExtension = dto.getExtension();
        if(fileExtension==null || fileExtension.isEmpty()) {
            int lastDotPos = dto.getName().lastIndexOf('.');
            if (lastDotPos > 0) {
                fileExtension = dto.getName().substring(lastDotPos + 1);
            }
        }
        AttachmentContent attachmentContent = attachmentContentGetService.get(dto.getAttachmentContentId()); // должно стать lazy load в будущем
        Long taskId = dto.getTaskId();
        Task task = null;
        //if (taskId != null) taskGetService.get(taskId); // раскомментировать только после внедрения lazy load
        return new TaskAttachment(dto.getId(), dto.getTaskId(), task, dto.getAttachmentContentId(),
                attachmentContent, dto.getName(), fileExtension, dto.getSize(), dto.getDateCreated());
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public TaskAttachmentDto toDto(TaskAttachment model) {
        return new TaskAttachmentDto(model.getId(), model.getTaskId(), model.getAttachmentContentId(),
                model.getName(), model.getExtension(), model.getSize(), model.getDateCreated());
    }

}

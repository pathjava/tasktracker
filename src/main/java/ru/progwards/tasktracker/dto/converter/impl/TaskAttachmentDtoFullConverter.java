package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.TaskAttachmentDtoFull;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.model.TaskAttachmentContent;
import ru.progwards.tasktracker.service.GetService;


/**
 * Преобразование valueObject <-> dto
 *
 * TaskAttachment <-> TaskAttachmentDtoFull
 *
 * @author Gregory Lobkov
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskAttachmentDtoFullConverter implements Converter<TaskAttachment, TaskAttachmentDtoFull> {

    private final GetService<Long, TaskAttachment> getService;
    private final Converter<Task, TaskDtoPreview> taskDtoConverter;
//    private final GetService<Long, TaskAttachmentContent> contentGetService;
//    private final Converter<TaskAttachmentContent, TaskAttachmentContentDtoPreview> contentDtoConverter;

    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public TaskAttachment toModel(TaskAttachmentDtoFull dto) {
        TaskAttachment model = null;
        if (dto != null) {
            Task task = taskDtoConverter.toModel(dto.getTask());
            TaskAttachmentContent content = null;//dto.getContent()==null?null:contentGetService.get(dto.getContent().getId());
            if (dto.getId() == null) {
                model = new TaskAttachment(
                        null,
                        task,
                        dto.getName(),
                        dto.getExtension(),
                        dto.getSize(),
                        dto.getCreated(),
                        content
                );
            } else {
                model = getService.get(dto.getId());
                model.setTask(task);
                model.setName(dto.getName());
                model.setExtension(dto.getExtension());
                model.setSize(dto.getSize());
                model.setCreated(dto.getCreated());
                model.setContent(content);
            }
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
    public TaskAttachmentDtoFull toDto(TaskAttachment model) {
        TaskAttachmentDtoFull dto = null;
        if (model != null) {
            dto = new TaskAttachmentDtoFull(
                    model.getId(),
                    taskDtoConverter.toDto(model.getTask()),
                    model.getName(),
                    model.getExtension(),
                    model.getSize(),
                    model.getCreated()
                    //,contentDtoConverter.toDto(model.getContent())
            );
        }
        return dto;
    }

}

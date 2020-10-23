package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.TaskAttachmentEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.AttachmentContent;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.TaskAttachment;

import java.time.Instant;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;

/**
 * Преобразование valueObject <-> entity
 *
 * TaskAttachment <-> TaskAttachmentEntity
 *
 * @author Gregory Lobkov
 */
@Component
public class TaskAttachmentConverter implements Converter<TaskAttachmentEntity, TaskAttachment> {

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
     * <p>
     * Извлекаем расширение файла,
     * получаем бизнес-объект AttachmentContent,
     * получаем бизнес-объект Task
     *
     * @param entity сущность репозитория
     * @return бизнес-объект
     */
    @Override
    public TaskAttachment toVo(TaskAttachmentEntity entity) {
        String fileExtension = "";
        int lastDotPos = entity.getName().lastIndexOf('.');
        if (lastDotPos > 0) {
            fileExtension = entity.getName().substring(lastDotPos + 1);
        }
        AttachmentContent attachmentContent = attachmentContentGetService.get(entity.getAttachmentContentId()); // должно стать lazy load в будущем
        Long taskId = entity.getTaskId();
        Task task = null;
        //if (taskId != null) taskGetService.get(taskId); // раскомментировать только после внедрения lazy load
        return new TaskAttachment(entity.getId(), entity.getTaskId(), task, entity.getAttachmentContentId(),
                attachmentContent, entity.getName(), fileExtension, entity.getSize(),
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(entity.getDateCreated()), UTC));
    }

    /**
     * Преобразовать в сущность хранилища
     * <p>
     * Содержимое, подкрепленное к бизнес-сущности не учитывается
     *
     * @param vo бизнес-объект
     * @return сущность репозитория
     */
    @Override
    public TaskAttachmentEntity toEntity(TaskAttachment vo) {
        return new TaskAttachmentEntity(vo.getId(), vo.getTaskId(), vo.getAttachmentContentId(),
                vo.getName(), vo.getExtension(), vo.getSize(), vo.getDateCreated().toEpochSecond());
    }

}
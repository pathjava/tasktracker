package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.TaskAttachmentEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.AttachmentContent;
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
        AttachmentContent attachmentContent = attachmentContentGetService.get(entity.getContentId()); // должно стать lazy load в будущем
        return new TaskAttachment(entity.getId(), entity.getTaskId(), entity.getName(), fileExtension, entity.getSize(),
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(entity.getCreated()), UTC),
                entity.getContentId(), attachmentContent);
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
        return new TaskAttachmentEntity(vo.getId(), vo.getTaskId(), vo.getName(), vo.getExtension(),
                vo.getSize(), vo.getCreated().toEpochSecond(), vo.getContentId());
    }

}
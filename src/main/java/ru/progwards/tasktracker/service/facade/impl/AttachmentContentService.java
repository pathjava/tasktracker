package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByParentId;
import ru.progwards.tasktracker.repository.entity.AttachmentContentEntity;
import ru.progwards.tasktracker.repository.entity.TaskAttachmentEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.AttachmentContent;

import java.util.Collection;

/**
 * Бизнес-логика работы с содержимым вложений
 *
 * @author Gregory Lobkov
 */
@Service
public class AttachmentContentService implements CreateService<AttachmentContent>, RemoveService<AttachmentContent>, GetService<Long, AttachmentContent> {

    @Autowired
    private Repository<Long, AttachmentContentEntity> repository;
    @Autowired
    private RepositoryByParentId<Long, TaskAttachmentEntity> taskAttachmentRepositoryByParentId;
    @Autowired
    private Converter<AttachmentContentEntity, AttachmentContent> attachmentContentConverter;


    /**
     * Сохранение файла в таблицу вложений
     *
     * @param attachment вложение
     */
    @Override
    public void create(AttachmentContent attachment) {
        AttachmentContentEntity entity = attachmentContentConverter.toEntity(attachment);
        repository.create(entity);
        attachment.setId(entity.getId()); // переложим идентификатор в бизнес-объект
    }


    /**
     * Удаление файла из таблицы вложений
     *
     * @param attachment вложение
     */
    @Override
    public void remove(AttachmentContent attachment) {
        Long id = attachment.getId();
        // проверить, имеются ли TaskAttachment, в которых TaskAttachment.content_id = AttachmentContent.id
        Collection<TaskAttachmentEntity> taskAttachmentEntities = taskAttachmentRepositoryByParentId.getByParentId(id);
        if(taskAttachmentEntities.size() == 0) {
            // удалить, если связей нет
            repository.delete(id);
        }
    }


    /**
     * Получить файл-вложение для передачи пользователю
     *
     * @param id идентификатор
     * @return описание файла-вложения вместе с содержимым
     */
    @Override
    public AttachmentContent get(Long id) {
        return attachmentContentConverter.toVo(repository.get(id));
    }

}

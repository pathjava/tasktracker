package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.AttachmentContentEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.AttachmentContent;

/**
 * Бизнес-логика работы с содержимым вложений
 *
 * @author Gregory Lobkov
 */
@Service
public class AttachmentContentService implements CreateService<AttachmentContent>, RemoveService<AttachmentContent>, GetService<Long, AttachmentContent> {

    @Autowired
    private Repository<Long, AttachmentContentEntity> attachmentRepository;
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
        attachmentRepository.create(entity);
        attachment.setId(entity.getId()); // переложим идентификатор в бизнес-объект
    }


    /**
     * Удаление файла из таблицы вложений
     *
     * @param attachment вложение
     */
    @Override
    public void remove(AttachmentContent attachment) {
        attachmentRepository.delete(attachment.getId());
    }


    /**
     * Получить файл-вложение для передачи пользователю
     *
     * @param id идентификатор
     * @return описание файла-вложения вместе с содержимым
     */
    @Override
    public AttachmentContent get(Long id) {
        return attachmentContentConverter.toVo(attachmentRepository.get(id));
    }

}

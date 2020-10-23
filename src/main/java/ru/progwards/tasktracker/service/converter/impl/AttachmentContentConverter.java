package ru.progwards.tasktracker.service.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.entity.AttachmentContentEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.AttachmentContent;

import java.io.ByteArrayOutputStream;


/**
 * Преобразование valueObject <-> entity
 *
 * AttachmentContent <-> AttachmentContentEntity
 *
 * @author Gregory Lobkov
 */
@Component
public class AttachmentContentConverter implements Converter<AttachmentContentEntity, AttachmentContent> {


    /**
     * Преобразовать в бизнес-объект
     *
     * @param entity сущность репозитория
     * @return бизнес-объект
     */
    @Override
    public AttachmentContent toVo(AttachmentContentEntity entity) {
        byte[] bytes = entity.getData();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
        baos.write(bytes, 0, bytes.length);
        return new AttachmentContent(entity.getId(), baos);
    }


    /**
     * Преобразовать в сущность хранилища
     *
     * @param vo бизнес-объект
     * @return сущность репозитория
     */
    @Override
    public AttachmentContentEntity toEntity(AttachmentContent vo) {
        return new AttachmentContentEntity(vo.getId(), vo.getData().toByteArray());
    }

}

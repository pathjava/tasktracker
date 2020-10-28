package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.AttachmentContentDto;
import ru.progwards.tasktracker.service.vo.AttachmentContent;


/**
 * Преобразование valueObject <-> dto
 *
 * AttachmentContent <-> AttachmentContentDto
 *
 * @author Gregory Lobkov
 */
@Component
public class AttachmentContentDtoConverter implements Converter<AttachmentContent, AttachmentContentDto> {


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public AttachmentContent toModel(AttachmentContentDto dto) {
        return new AttachmentContent(dto.getId(), dto.getData());
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public AttachmentContentDto toDto(AttachmentContent model) {
        return new AttachmentContentDto(model.getId(), model.getData());
    }

}

package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.TaskAttachmentContentDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.TaskAttachmentContent;


/**
 * Преобразование valueObject <-> dto
 *
 * AttachmentContent <-> AttachmentContentDtoFull
 *
 * @author Gregory Lobkov
 */
@Component
public class AttachmentContentDtoFullConverter implements Converter<TaskAttachmentContent, TaskAttachmentContentDtoFull> {


    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public TaskAttachmentContent toModel(TaskAttachmentContentDtoFull dto) {
        //return new AttachmentContent(dto.getId(), dto.getData());
        //https://stackoverflow.com/questions/20614973/read-write-blob-data-in-chunks-with-hibernate
        return null;
    }


    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public TaskAttachmentContentDtoFull toDto(TaskAttachmentContent model) {
        //https://stackoverflow.com/questions/20614973/read-write-blob-data-in-chunks-with-hibernate
        //return new AttachmentContentDtoFull(model.getId(), model.getData());
        return null;
    }

}

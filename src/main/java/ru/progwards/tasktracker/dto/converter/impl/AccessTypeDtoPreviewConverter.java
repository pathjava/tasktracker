package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.AccessTypeDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.types.AccessType;

/**
 * * Преобразование ENUM valueObject <-> dto
 *
 * @author Artem Dikov
 */
@Component
public class AccessTypeDtoPreviewConverter implements Converter<AccessType, AccessTypeDtoPreview> {
    @Override
    public AccessType toModel(AccessTypeDtoPreview dto) {
        return (dto == null) ? null : AccessType.valueOf(dto.getName());
    }

    @Override
    public AccessTypeDtoPreview toDto(AccessType model) {
        return new AccessTypeDtoPreview(model.toString());
    }
}

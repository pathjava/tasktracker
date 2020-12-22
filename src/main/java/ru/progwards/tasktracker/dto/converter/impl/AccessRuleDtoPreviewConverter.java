package ru.progwards.tasktracker.dto.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.AccessRuleDtoPreview;
import ru.progwards.tasktracker.model.AccessRule;

@Component
public class AccessRuleDtoPreviewConverter implements Converter<AccessRule, AccessRuleDtoPreview> {
    @Override
    public AccessRule toModel(AccessRuleDtoPreview dto) {
        return null;
    }

    @Override
    public AccessRuleDtoPreview toDto(AccessRule model) {
        return null;
    }
}

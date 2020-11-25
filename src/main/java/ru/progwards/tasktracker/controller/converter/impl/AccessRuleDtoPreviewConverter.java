package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.AccessRuleDtoPreview;
import ru.progwards.tasktracker.service.vo.AccessRule;

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

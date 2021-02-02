package ru.progwards.tasktracker.dto.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.AccessRuleDtoFull;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.repository.UserRoleRepository;

import java.util.Optional;

/**
 * @author Artem Dikov, Konstantin Kishkin
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessRuleDtoFullConverter implements Converter<AccessRule, AccessRuleDtoFull> {

    private final UserRoleRepository userRoleRepository;

    @Override
    public AccessRule toModel(AccessRuleDtoFull dto) {
        if (dto == null)
            return null;
        Optional<UserRole> optionalUserRole = userRoleRepository.findById(dto.getUserRoleId());
        return new AccessRule(dto.getId(), dto.getObjectName(), dto.getPropertyName(), dto.getObjectId(),
                dto.getAccessType(), optionalUserRole.get(), dto.getAccessObject());
    }

    @Override
    public AccessRuleDtoFull toDto(AccessRule model) {
        if (model == null)
            return null;
        return new AccessRuleDtoFull(model.getId(), model.getObjectName(), model.getPropertyName(), model.getObjectId(),
                model.getAccessType(), model.getUserRole().getId(), model.getObject());
    }
}

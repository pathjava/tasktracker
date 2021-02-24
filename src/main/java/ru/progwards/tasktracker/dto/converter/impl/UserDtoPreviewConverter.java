package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.service.GetService;

/**
 * Преобразование valueObject <-> dto
 * User <-> UserDtoFull
 *
 * @author Aleksandr Sidelnikov
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class UserDtoPreviewConverter implements Converter<User, UserDtoPreview> {

    private final GetService<Long, User> userGetService;

    @Override
    public User toModel(UserDtoPreview dto) {
        return userGetService.get(dto.getId());
    }

    @Override
    public UserDtoPreview toDto(User model) {
        return new UserDtoPreview(
                model.getId(),
                model.getName()
        );
    }
}

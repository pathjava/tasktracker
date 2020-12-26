package ru.progwards.tasktracker.dto.converter.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.UserDtoFull;
import ru.progwards.tasktracker.dto.UserRoleDtoPreview;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.service.GetService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Преобразование valueObject <-> dto
 * User <-> UserDtoFull
 * @author Aleksandr Sidelnikov
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDtoFullConverter implements Converter<User, UserDtoFull> {

    private final @NonNull Converter<UserRole, UserRoleDtoPreview> userRoleDtoConverter;
    private final @NonNull GetService<Long, User> userGetService;

    /**
     * Преобразовать в бизнес-объект
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public User toModel(UserDtoFull dto) {
        User user;
        if(dto.getId()!=null) {
            user = userGetService.get(dto.getId());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            String newPass = dto.getPassword();
            if(newPass!=null && !newPass.isEmpty()) {
                user.setPassword(newPass);
            }
        } else {
            user = new User(null,
                    dto.getName(),
                    dto.getEmail(),
                    dto.getPassword(),
                    null,null,null,null,null,null,null);
        }
        return user;
    }

    /**
     * Метод конвертирует лист из Dto в VO
     * @param users лист Dto UserRole
     * @return лист VO UserRole
     */
    private List<UserRole> listDtoToVoUserRole(List<UserRoleDtoPreview> users) {
        return users.stream()
                .map(userRoleDtoConverter::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Преобразовать в сущность dto
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public UserDtoFull toDto(User model) {
        return new UserDtoFull(model.getId(),
                model.getName(),
                model.getEmail(),
                "",
                listVoToDtoUserRole(model.getRoles()));
    }

    /**
     * Метод конвертирует лист из VO в Dto
     * @param users лист VO UserRole задачи
     * @return лист Dto UserRole задачи
     */
    private List<UserRoleDtoPreview> listVoToDtoUserRole(List<UserRole> users) {
        return users.stream()
                .map(userRoleDtoConverter::toDto)
                .collect(Collectors.toList());
    }

}
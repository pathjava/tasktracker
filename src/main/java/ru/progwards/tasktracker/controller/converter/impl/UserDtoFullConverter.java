package ru.progwards.tasktracker.controller.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.UserDtoFull;
import ru.progwards.tasktracker.service.facade.GetListByParentService;
import ru.progwards.tasktracker.service.vo.WorkFlowAction;
import ru.progwards.tasktracker.service.vo.User;

/**
 * Преобразование valueObject <-> dto
 *
 * User <-> UserDtoFull
 *
 * @author Aleksandr Sidelnikov
 */
@Component
public class UserDtoFullConverter implements Converter<User, UserDtoFull> {

    /**
     * Сервис получения списка действий по статусу
     */
    @Autowired
    private GetListByParentService<Long, WorkFlowAction> workFlowActionGetListByParentService;

    /**
     * Преобразовать в бизнес-объект
     *
     * @param dto сущность dto
     * @return бизнес-объект
     */
    @Override
    public User toModel(UserDtoFull dto) {
        return new User(dto.getId(),
                dto.getName(),
                dto.getEmail(), dto.getPassword(),
                dto.getRoles());
    }

    /**
     * Преобразовать в сущность dto
     *
     * @param model бизнес-объект
     * @return сущность dto
     */
    @Override
    public UserDtoFull toDto(User model) {
        return new UserDtoFull(model.getId(),
                model.getName(), model.getEmail(), model.getPassword(),
        model.getRoles());
    }

}
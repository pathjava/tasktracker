package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.types.SystemRole;
import ru.progwards.tasktracker.repository.AccessRuleRepository;
import ru.progwards.tasktracker.repository.UserRoleRepository;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.entity.UserRoleEntity;
//import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.UserRole;

import java.util.*;

/**
 * @author Artem Dikov
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleService implements CreateService<UserRole>, GetListService<UserRole>, GetService<Long, UserRole>,
        RefreshService<UserRole>, RemoveService<UserRole>, TemplateService<UserRole> {

    private final UserRoleRepository userRoleRepository;
    private final CreateService<UserRole> userRoleCreateService;
    private final TemplateService<AccessRule> accessRuleTemplateService;

    @Override
    public void create(UserRole model) {
        userRoleRepository.save(model);
    }

    @Override
    public List<UserRole> getList() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole get(Long id) {
        return userRoleRepository.findById(id).get();
    }

    @Override
    public void refresh(UserRole model) {
        userRoleRepository.save(model);
    }

    @Override
    public void remove(UserRole model) {
        userRoleRepository.deleteById(model.getId());
    }

    public UserRole getByName(String name) {
        return userRoleRepository.findByName(name);
    }

//    /**
//     * Метод создания UserRole по шаблону
//     *
//     * @param args – [0] - SystemRole, [1] - User(менеджер проектов), [2] - User
//     */

    @Override
    public List<UserRole> createFromTemplate(Object... args) {

        List<UserRole> result = new ArrayList<>();
//        if (args.length != 2)
//            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: arguments expected");
//        if (!(args[0] instanceof SystemRole))
//            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 0 must be SystemRole");
//        if (!(args[1] instanceof User))
//            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 1 must be User");
//        if (!(args[2] instanceof User))
//            throw new OperationIsNotPossibleException("TaskNote.createFromTemplate: argument 2 must be User");


        UserRole userRole1 = new UserRole();
        userRole1.setSystemRole(SystemRole.ADMIN);
        userRole1.setName("Администраторы");
        userRoleCreateService.create(userRole1);

        UserRole userRole2 = new UserRole();
        userRole2.setSystemRole(SystemRole.USER);
        userRole2.setAccessRules(accessRuleTemplateService.createFromTemplate());
        userRoleCreateService.create(userRole2);
        result.add(userRole1);
        result.add(userRole2);
        return result;
    }
}
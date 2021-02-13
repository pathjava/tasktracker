package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.model.types.SystemRole;
import ru.progwards.tasktracker.repository.UserRoleRepository;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Artem Dikov, Konstantin Kishkin
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class UserRoleService implements CreateService<UserRole>, GetListService<UserRole>, GetService<Long, UserRole>,
        RefreshService<UserRole>, RemoveService<UserRole>, TemplateService<UserRole> {

    private final UserRoleRepository userRoleRepository;
    private final AccessRuleService accessRuleService;

    @Transactional
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
        return userRoleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("UserRole id=%s not found", id)));
    }

    @Transactional
    @Override
    public void refresh(UserRole model) {
        userRoleRepository.save(model);
    }

    @Transactional
    @Override
    public void remove(UserRole model) {
        userRoleRepository.deleteById(model.getId());
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
        create(userRole1);

        UserRole userRole2 = new UserRole();
        userRole2.setSystemRole(SystemRole.USER);
        userRole2.setAccessRules(accessRuleService.createFromTemplate());
        create(userRole2);
        result.add(userRole1);
        result.add(userRole2);
        return result;
    }
}

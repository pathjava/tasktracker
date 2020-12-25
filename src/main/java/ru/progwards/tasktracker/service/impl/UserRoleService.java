package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        RefreshService<UserRole>, RemoveService<UserRole> {

    @NonNull
    private final UserRoleRepository userRoleRepository;

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
}

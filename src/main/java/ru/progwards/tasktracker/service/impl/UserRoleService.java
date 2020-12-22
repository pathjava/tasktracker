package ru.progwards.tasktracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.UserRoleEntity;
import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.UserRole;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRoleService implements CreateService<UserRole>, GetListService<UserRole>, GetService<Long, UserRole>,
        RefreshService<UserRole>, RemoveService<UserRole> {

    @Autowired
    private Repository<Long, UserRoleEntity> userRoleRepository;
    @Autowired
    private Converter<UserRoleEntity, UserRole> userRoleConverter;

    @Override
    public void create(UserRole model) {
        userRoleRepository.create(userRoleConverter.toEntity(model));
    }

    @Override
    public List<UserRole> getList() {
        return userRoleRepository.get().stream()
                .map(entity -> userRoleConverter.toVo(entity))
                .collect(Collectors.toList());
    }

    @Override
    public UserRole get(Long id) {
        return userRoleConverter.toVo(userRoleRepository.get(id));
    }

    @Override
    public void refresh(UserRole model) {
        userRoleRepository.update(userRoleConverter.toEntity(model));
    }

    @Override
    public void remove(UserRole model) {
        userRoleRepository.delete(model.getId());
    }
}

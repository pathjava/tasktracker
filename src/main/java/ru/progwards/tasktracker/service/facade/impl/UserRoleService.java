package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.UserRoleEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRoleService implements CreateService<UserRole>, GetListService<UserRole>, GetService<Long, UserRole>,
        RefreshService<UserRole>, RemoveService<UserRole>, AddDetailingService<UserRole, AccessRule>,
        DeleteDetailingService<UserRole, Long>, UpdateDetailingService<UserRole, AccessRule> {

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

    @Override
    public void addDetailing(UserRole valueObject, Collection<? extends AccessRule> detailing) {
        Map<Long, AccessRule> ruleMap = valueObject.getAccessRules();
        for (AccessRule newRule : detailing) {
            ruleMap.putIfAbsent(newRule.getId(), newRule);
        }
        this.refresh(valueObject);
    }

    @Override
    public void deleteDetailing(UserRole valueObject, Collection<? extends Long> detailing) {
        Map<Long, AccessRule> ruleMap = valueObject.getAccessRules();
        for (Long ruleId : detailing) {
            ruleMap.remove(ruleId);
        }
        this.refresh(valueObject);
    }

    @Override
    public void updateDetailing(UserRole valueObject, Collection<? extends AccessRule> detailing) {
        Map<Long, AccessRule> ruleMap = valueObject.getAccessRules();
        for (AccessRule rule : detailing) {
            if (ruleMap.containsKey(rule.getId()))
                ruleMap.put(rule.getId(), rule);
        }
        this.refresh(valueObject);
    }
}

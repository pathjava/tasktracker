package ru.progwards.tasktracker.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.UserRoleRepository;
import ru.progwards.tasktracker.service.converter.impl.UserRoleConverter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.UserRole;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRoleService  implements CreateService<UserRole>, GetListService<UserRole>, GetService<Long, UserRole>,
        RefreshService<UserRole>, RemoveService<UserRole> {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRoleConverter userRoleConverter;


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

    public void addRules(UserRole userRole, Collection<? extends AccessRule> newRules) {
        Map<Long, AccessRule> ruleMap = userRole.getAccessRules();
        for (AccessRule newRule : newRules) {
            ruleMap.putIfAbsent(newRule.getId(), newRule);
        }
        this.refresh(userRole);
    }

    public void deleteRules(UserRole userRole, Collection<Long> ruleIds) {
        Map<Long, AccessRule> ruleMap = userRole.getAccessRules();
        for (Long ruleId : ruleIds) {
            ruleMap.remove(ruleId);
        }
        this.refresh(userRole);
    }

    public void updateRules(UserRole userRole, Collection<? extends AccessRule> rulesToUpdate) {
        Map<Long, AccessRule> ruleMap = userRole.getAccessRules();
        for (AccessRule rule : rulesToUpdate) {
            ruleMap.put(rule.getId(), rule);
        }
        this.refresh(userRole);
    }
}

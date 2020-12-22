package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.AccessRuleEntity;
import ru.progwards.tasktracker.repository.deprecated.entity.UserRoleEntity;
import ru.progwards.tasktracker.model.types.SystemRole;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class UserRoleEntityRepository implements Repository<Long, UserRoleEntity>, InitializingBean {

    private final Map<Long, UserRoleEntity> roles = new ConcurrentHashMap<>();

    @Autowired
    private Repository<Long, AccessRuleEntity> accessRuleRepository;

    @Override
    public void afterPropertiesSet() {
        List<AccessRuleEntity> accessRules = new ArrayList<>();
        accessRules.addAll(accessRuleRepository.get());
        UserRoleEntity role = new UserRoleEntity(1L, "role_name", SystemRole.MEMBER, accessRules);
        roles.put(role.getId(), role);
    }

    @Override
    public Collection<UserRoleEntity> get() {
        return roles.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public UserRoleEntity get(Long id) {
        return roles.get(id);
    }

    @Override
    public void create(UserRoleEntity role) {
        if (role.getId() == null)
            role.setId(new Random().nextLong());
        roles.putIfAbsent(role.getId(), role);
    }

    @Override
    public void update(UserRoleEntity role) {
        if (roles.containsKey(role.getId()))
            roles.put(role.getId(), role);
    }

    @Override
    public void delete(Long id) {
        roles.remove(id);
    }

}

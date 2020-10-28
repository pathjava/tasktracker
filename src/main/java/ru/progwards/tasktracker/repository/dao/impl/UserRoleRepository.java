package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.AccessRuleEntity;
import ru.progwards.tasktracker.repository.entity.UserRoleEntity;
import ru.progwards.tasktracker.util.types.SystemRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class UserRoleRepository implements Repository<Long, UserRoleEntity>, InitializingBean {

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

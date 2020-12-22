package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.UserRole;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class UserRoleRepository implements Repository<Long, UserRole>, InitializingBean {

    private final Map<Long, UserRole> roles = new ConcurrentHashMap<>();

    @Autowired
    private Repository<Long, AccessRule> accessRuleRepository;

//    @Autowired
//    private TaskTypeRepository<Task, Long> repository;

    /**
     *
     */
    @Override
    public void afterPropertiesSet() {
//        List<AccessRuleEntity> accessRules = new ArrayList<>();
//        accessRules.addAll(accessRuleRepository.get());
//        UserRole role = new UserRole(1L, "role_name", SystemRole.MEMBER, accessRules);
//        roles.put(role.getId(), role);
    }

    /**
     * @return
     */
    @Override
    public Collection<UserRole> get() {
        return roles.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param id идентификатор
     * @return
     */
    @Override
    public UserRole get(Long id) {
        return roles.get(id);
    }

    /**
     * @param entity
     */
    @Override
    public void create(UserRole entity) {
        if (entity.getId() == null)
            entity.setId(new Random().nextLong());
        roles.putIfAbsent(entity.getId(), entity);
    }

    /**
     * @param entity
     */
    @Override
    public void update(UserRole entity) {
        if (roles.containsKey(entity.getId()))
            roles.put(entity.getId(), entity);
    }

    /**
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
        roles.remove(id);
    }

}

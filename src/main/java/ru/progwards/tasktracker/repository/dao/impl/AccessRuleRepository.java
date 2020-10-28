package ru.progwards.tasktracker.repository.dao.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.AccessRuleEntity;
import ru.progwards.tasktracker.util.types.AccessType;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class AccessRuleRepository implements Repository<Long, AccessRuleEntity>, InitializingBean {

    private final Map<Long, AccessRuleEntity> rules = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() {
        AccessRuleEntity rule1 = new AccessRuleEntity(1L, "project", "workflow",
                1L, AccessType.READ_ONLY);
        rules.put(rule1.getId(), rule1);
        AccessRuleEntity rule2 = new AccessRuleEntity(2L, "project", "workflow",
                123L, AccessType.MODIFY);
        rules.put(rule2.getId(), rule2);
    }

    @Override
    public Collection<AccessRuleEntity> get() {
        return rules.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public AccessRuleEntity get(Long id) {
        return rules.get(id);
    }

    @Override
    public void create(AccessRuleEntity rule) {
        rules.putIfAbsent(rule.getId(), rule);
    }

    @Override
    public void update(AccessRuleEntity rule) {
        if (rules.containsKey(rule.getId()))
            rules.put(rule.getId(), rule);
    }

    @Override
    public void delete(Long id) {
        rules.remove(id);
    }
}

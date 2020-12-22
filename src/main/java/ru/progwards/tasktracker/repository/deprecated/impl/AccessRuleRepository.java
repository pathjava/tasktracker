package ru.progwards.tasktracker.repository.deprecated.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.TaskTypeRepository;
import ru.progwards.tasktracker.model.AccessRule;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@org.springframework.stereotype.Repository
public class AccessRuleRepository implements Repository<Long, AccessRule>/*, InitializingBean */{

//    @Autowired
//    private TaskTypeRepository<AccessRule, Long> repository;

    private final Map<Long, AccessRule> rules = new ConcurrentHashMap<>();

//    /**
//     *
//     */
//    @Override
//    public void afterPropertiesSet() {
//        AccessRule rule1 = new AccessRule(1L, "project", "workflow",
//                1L, AccessType.READ_ONLY);
//        rules.put(rule1.getId(), rule1);
//        AccessRule rule2 = new AccessRule(2L, "project", "workflow",
//                123L, AccessType.MODIFY);
//        rules.put(rule2.getId(), rule2);
//    }

    /**
     * @return
     */
    @Override
    public Collection<AccessRule> get() {
        return rules.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * @param id идентификатор
     * @return
     */
    @Override
    public AccessRule get(Long id) {
        return rules.get(id);
    }

    /**
     * @param entity
     */
    @Override
    public void create(AccessRule entity) {
        if (entity.getId() == null)
            entity.setId(new Random().nextLong());
        rules.putIfAbsent(entity.getId(), entity);
    }

    /**
     * @param entity
     */
    @Override
    public void update(AccessRule entity) {
        if (rules.containsKey(entity.getId()))
            rules.put(entity.getId(), entity);
    }

    /**
     * @param id идентификатор удаляемой сущности
     */
    @Override
    public void delete(Long id) {
        rules.remove(id);
    }
}

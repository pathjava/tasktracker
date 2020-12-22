package ru.progwards.tasktracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.AccessRuleEntity;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.AccessRule;

import java.util.List;

@Service
public class AccessRuleService implements CreateService<AccessRule>, GetListService<AccessRule>, GetService<Long, AccessRule>,
        RefreshService<AccessRule>, RemoveService<AccessRule> {

    @Autowired
    private Repository<Long, AccessRuleEntity> accessRuleRepository;
//    @Autowired
//    private Converter<AccessRule, AccessRule> accessRuleConverter;


    @Override
    public void create(AccessRule model) {
//        accessRuleRepository.create(accessRuleConverter.toEntity(model));
    }

    @Override
    public List<AccessRule> getList() {
//        return accessRuleRepository.get().stream()
//                .map(entity -> accessRuleConverter.toVo(entity))
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public AccessRule get(Long id) {
//        return accessRuleConverter.toVo(accessRuleRepository.get(id));
        return null;
    }

    @Override
    public void refresh(AccessRule model) {
//        accessRuleRepository.update(accessRuleConverter.toEntity(model));
    }

    @Override
    public void remove(AccessRule model) {
        accessRuleRepository.delete(model.getId());
    }
}

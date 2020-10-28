package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.AccessRuleEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.AccessRule;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessRuleService implements CreateService<AccessRule>, GetListService<AccessRule>, GetService<Long, AccessRule>,
        RefreshService<AccessRule>, RemoveService<AccessRule> {

    @Autowired
    private Repository<Long, AccessRuleEntity> accessRuleRepository;
    @Autowired
    private Converter<AccessRuleEntity, AccessRule> accessRuleConverter;


    @Override
    public void create(AccessRule model) {
        accessRuleRepository.create(accessRuleConverter.toEntity(model));
    }

    @Override
    public List<AccessRule> getList() {
        return accessRuleRepository.get().stream()
                .map(entity -> accessRuleConverter.toVo(entity))
                .collect(Collectors.toList());
    }

    @Override
    public AccessRule get(Long id) {
        return accessRuleConverter.toVo(accessRuleRepository.get(id));
    }

    @Override
    public void refresh(AccessRule model) {
        accessRuleRepository.update(accessRuleConverter.toEntity(model));
    }

    @Override
    public void remove(AccessRule model) {
        accessRuleRepository.delete(model.getId());
    }
}

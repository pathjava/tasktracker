package ru.progwards.tasktracker.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.repository.dao.impl.AccessRuleRepository;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.impl.AccessRuleConverter;
import ru.progwards.tasktracker.service.converter.impl.ConverterTask;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.AccessRule;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccessRuleService implements CreateService<AccessRule>, GetListService<AccessRule>, GetService<Long, AccessRule>,
        RefreshService<AccessRule>, RemoveService<AccessRule> {

    @Autowired
    private AccessRuleRepository accessRuleRepository;
    @Autowired
    private AccessRuleConverter accessRuleConverter;


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

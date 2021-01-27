package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.AccessRuleRepository;
//import ru.progwards.tasktracker.repository.deprecated.entity.AccessRuleEntity;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.AccessRule;

import java.util.List;

/**
 * @author Artem Dikov
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessRuleService implements CreateService<AccessRule>, GetListService<AccessRule>, GetService<Long, AccessRule>,
        RefreshService<AccessRule>, RemoveService<AccessRule> {

    @NonNull
    private final AccessRuleRepository accessRuleRepository;

    @Override
    public void create(AccessRule model) {
        accessRuleRepository.save(model);
    }

    @Override
    public List<AccessRule> getList() {
        return accessRuleRepository.findAll();
    }

    @Override
    public AccessRule get(Long id) {
        return accessRuleRepository.findById(id).get();
    }

    @Override
    public void refresh(AccessRule model) {
        accessRuleRepository.saveAndFlush(model);
    }

    @Override
    public void remove(AccessRule model) {
        accessRuleRepository.deleteById(model.getId());
    }
}

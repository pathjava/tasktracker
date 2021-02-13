package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.AccessRule;
import ru.progwards.tasktracker.model.types.AccessObject;
import ru.progwards.tasktracker.model.types.AccessType;
import ru.progwards.tasktracker.repository.AccessRuleRepository;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Artem Dikov, Konstantin Kishkin
 */

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class AccessRuleService implements CreateService<AccessRule>, GetListService<AccessRule>, GetService<Long, AccessRule>,
        RefreshService<AccessRule>, RemoveService<AccessRule>, TemplateService<AccessRule> {

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
        return accessRuleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("AccessRule id=%s not found", id)));
    }

    @Override
    public void refresh(AccessRule model) {
        accessRuleRepository.saveAndFlush(model);
    }

    @Override
    public void remove(AccessRule model) {
        accessRuleRepository.deleteById(model.getId());
    }

    /**
     * Метод создания AccessRule по шаблону
     */
    @Transactional
    @Override
    public List<AccessRule> createFromTemplate(Object... args) {
        List<AccessRule> accessRuleList = new ArrayList<>();
        for (AccessObject accessObject : AccessObject.values()) {
            if (accessObject != AccessObject.ACCESS_RULE
                    && accessObject != AccessObject.USER_ROLE
                    && accessObject != AccessObject.USER
            ) {
                AccessRule accessRule = new AccessRule();
                accessRule.setId(null);
                accessRule.setAccessType(AccessType.MODIFY);
                accessRule.setObject(accessObject);
                accessRule.setObjectId(null);
                accessRule.setObject(accessObject);
                accessRule.setPropertyName(null);
                accessRule.setUserRole(null);
                accessRuleList.add(accessRule);
            }
        }
        return accessRuleList;
    }
}

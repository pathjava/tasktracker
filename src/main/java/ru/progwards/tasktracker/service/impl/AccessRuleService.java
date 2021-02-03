package ru.progwards.tasktracker.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.model.types.AccessObject;
import ru.progwards.tasktracker.model.types.AccessType;
import ru.progwards.tasktracker.repository.AccessRuleRepository;
//import ru.progwards.tasktracker.repository.deprecated.entity.AccessRuleEntity;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.AccessRule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Dikov, Konstantin Kishkin
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessRuleService implements CreateService<AccessRule>, GetListService<AccessRule>, GetService<Long, AccessRule>,
        RefreshService<AccessRule>, RemoveService<AccessRule>, TemplateService<AccessRule> {

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

    /**
     * Метод создания AccessRule по шаблону
     *
     */
    @Transactional
    @Override
    public List<AccessRule> createFromTemplate(Object... args) {
        List<AccessRule> accessRuleList = new ArrayList<>();
        for (AccessObject accessObject : AccessObject.values()){
            if (accessObject == AccessObject.PROJECT || accessObject == AccessObject.TASK || accessObject == AccessObject.TASK_TYPE
            || accessObject == AccessObject.TASK_PRIORITY || accessObject == AccessObject.RELATED_TASK || accessObject == AccessObject.RELATION_TYPE
            || accessObject == AccessObject.WORKFLOW || accessObject == AccessObject.WORKFLOW_STATUS || accessObject == AccessObject.WORKFLOW_ACTION
            || accessObject == AccessObject.TASK_ATTACHMENT || accessObject == AccessObject.TASK_ATTACHMENT_CONTENT ||
                    accessObject == AccessObject.WORK_LOG || accessObject == AccessObject.TASK_NOTE){
                AccessRule accessRule = new AccessRule();
                accessRule.setId(null);
                accessRule.setAccessType(AccessType.MODIFY);
                accessRule.setObject(accessObject);
                accessRule.setObjectId(null);
                accessRule.setObjectName(accessObject.toString());
                accessRule.setPropertyName(null);
                accessRule.setUserRole(null);
                accessRuleList.add(accessRule);
            }
        }
        return accessRuleList;
    }
}

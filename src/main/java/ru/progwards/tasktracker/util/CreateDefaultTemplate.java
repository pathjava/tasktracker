package ru.progwards.tasktracker.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.exception.CreateTemplateException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.TemplateService;

import java.util.List;

/**
 * @author Konstantin Kishkin
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreateDefaultTemplate {

    private final GetListService <UserRole> userRoleGetListService;
    private final TemplateService<UserRole> userRoleTemplateService;
    private final GetListService <User> userGetListService;
    private final TemplateService<User> userTemplateService;
    private final GetListService <TaskPriority> taskPriorityGetListService;
    private final TemplateService<TaskPriority> taskPriorityTemplateService;
    private final GetListService <RelationType> relationTypeGetListService;
    private final TemplateService<RelationType> relationTypeTemplateService;
    private final GetListService <WorkFlow> workFlowGetListService;
    private final TemplateService<WorkFlow> workFlowTemplateService;
    private final GetListService <Project> projectGetListService;
    private final TemplateService<Project> projectTemplateService;


    public void exec(){
        List<TaskPriority> taskPriorityList = taskPriorityGetListService.getList();
        if(taskPriorityList.isEmpty()){
            taskPriorityList = taskPriorityTemplateService.createFromTemplate();
        }
        List<RelationType> relationTypeList = relationTypeGetListService.getList();
        if(relationTypeList.isEmpty()){
            relationTypeList = relationTypeTemplateService.createFromTemplate();
        }
        List<WorkFlow> workFlowList = workFlowGetListService.getList();
        if(workFlowList.isEmpty()){
            workFlowList = workFlowTemplateService.createFromTemplate();
        }
        List<UserRole> userRoleList = userRoleGetListService.getList();
        if(userRoleList.isEmpty()){
            userRoleList = userRoleTemplateService.createFromTemplate();
            if (userRoleList.isEmpty()){
                throw new CreateTemplateException("Лист создания шаблонов ролей пользователей пустой");
            }
        }
        List<User> userList = userGetListService.getList();
        if(userList.isEmpty()){
            userList = userTemplateService.createFromTemplate(userRoleList.get(0));
            if (userList.isEmpty()){
                throw new CreateTemplateException("Лист создания шаблонов пользователей пустой");
            }
        }
        List<Project> projectList = projectGetListService.getList();
        if(projectList.isEmpty()){
            projectList = projectTemplateService.createFromTemplate(userList.get(0));
            if (projectList.isEmpty()){
                throw new CreateTemplateException("Лист создания шаблонов проектов пустой");
            }
        }
    }
}

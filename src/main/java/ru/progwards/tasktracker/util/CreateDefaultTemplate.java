package ru.progwards.tasktracker.util;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
//@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
public class CreateDefaultTemplate {

    private final Logger loggerCreateTemplate = LoggerFactory.getLogger(CreateDefaultTemplate.class);

    @Autowired
    private GetListService <UserRole> userRoleGetListService;
    @Autowired
    private TemplateService<UserRole> userRoleTemplateService;
    @Autowired
    private GetListService <User> userGetListService;
    @Autowired
    private TemplateService<User> userTemplateService;
    @Autowired
    private GetListService <TaskPriority> taskPriorityGetListService;
    @Autowired
    private TemplateService<TaskPriority> taskPriorityTemplateService;
    @Autowired
    private GetListService <RelationType> relationTypeGetListService;
    @Autowired
    private TemplateService<RelationType> relationTypeTemplateService;
    @Autowired
    private GetListService <WorkFlow> workFlowGetListService;
    @Autowired
    private TemplateService<WorkFlow> workFlowTemplateService;
    @Autowired
    private GetListService <Project> projectGetListService;
    @Autowired
    private TemplateService<Project> projectTemplateService;

/*    private GetListService <UserRole> userRoleGetListService;
    private TemplateService<UserRole> userRoleTemplateService;
    private GetListService <User> userGetListService;
    private TemplateService<User> userTemplateService;
    private GetListService <TaskPriority> taskPriorityGetListService;
    private TemplateService<TaskPriority> taskPriorityTemplateService;
    private GetListService <RelationType> relationTypeGetListService;
    private TemplateService<RelationType> relationTypeTemplateService;
    private GetListService <WorkFlow> workFlowGetListService;
    private TemplateService<WorkFlow> workFlowTemplateService;
    private GetListService <Project> projectGetListService;
    private TemplateService<Project> projectTemplateService;*/

    public void exec(){
        long count;
        List<TaskPriority> taskPriorityList = taskPriorityGetListService.getList();
        count=taskPriorityList.size();
        if(taskPriorityList.isEmpty()){
            taskPriorityList = taskPriorityTemplateService.createFromTemplate();
            loggerCreateTemplate.info("Created: {} count = {}", "шаблоны приоритетов задач", count);
        }
        List<RelationType> relationTypeList = relationTypeGetListService.getList();
        count=relationTypeList.size();
        if(relationTypeList.isEmpty()){
            relationTypeList = relationTypeTemplateService.createFromTemplate();
            loggerCreateTemplate.info("Created: {} count = {}", "шаблоны типов отношений", count);
        }
        List<WorkFlow> workFlowList = workFlowGetListService.getList();
        count=workFlowList.size();
        if(workFlowList.isEmpty()){
            workFlowList = workFlowTemplateService.createFromTemplate();
            loggerCreateTemplate.info("Created: {} count = {}", "шаблоны workflow", count);
        }
        List<UserRole> userRoleList = userRoleGetListService.getList();
        count=userRoleList.size();
        if(userRoleList.isEmpty()){
            userRoleList = userRoleTemplateService.createFromTemplate();
            if (userRoleList.isEmpty()){
                throw new CreateTemplateException("Лист создания шаблонов ролей пользователей пустой");
            }
            loggerCreateTemplate.info("Created: {} count = {}", "шаблоны ролей пользователей", count);
        }
        List<User> userList = userGetListService.getList();
        count=userList.size();
        if(userList.isEmpty()){
            userList = userTemplateService.createFromTemplate(userRoleList.get(0));
            if (userList.isEmpty()){
                throw new CreateTemplateException("Лист создания шаблонов пользователей пустой");
            }
            loggerCreateTemplate.info("Created: {} count = {}", "шаблоны пользователей", count);
        }
        List<Project> projectList = projectGetListService.getList();
        count = projectList.size();
        if(projectList.isEmpty()){
            projectList = projectTemplateService.createFromTemplate(userList.get(0));
            if (projectList.isEmpty()){
                throw new CreateTemplateException("Лист создания шаблонов проектов пустой");
            }
            loggerCreateTemplate.info("Created: {} count = {}", "шаблоны проектов", count);
        }
        loggerCreateTemplate.info("CreateDefaultTemplate done.");
    }
}

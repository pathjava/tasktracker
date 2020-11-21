package ru.progwards.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.converter.impl.ProjectDtoConverter;
import ru.progwards.tasktracker.controller.dto.ProjectDto;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.impl.TaskPriorityService;
import ru.progwards.tasktracker.service.facade.impl.UserService;
import ru.progwards.tasktracker.service.facade.impl.project.ProjectCreateService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.TaskPriority;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.ZonedDateTime;
import java.util.ArrayList;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

//        CreateService<Project> projectCreateService = context.getBean(ProjectCreateService.class);
//
//        Converter<Project, ProjectDto> converter = context.getBean(ProjectDtoConverter.class);
//
//        UserService userService = context.getBean(UserService.class);
//
//        WorkFlow workFlow = new WorkFlow(1L, "name", false, 1L, null);
////
//        for (long i = 0; i < 5; i++) {
//            projectCreateService.create(converter.toModel(new ProjectDto(i, "name"+i, "desc"+i, "prefix",
//                    userService.get(i), ZonedDateTime.now(), workFlow, new ArrayList<>(), 0L)));
//        }

//        CreateService<TaskPriority> taskPriorityService = context.getBean(TaskPriorityService.class);
//
//        for (long i = 0; i < 5; i++) {
//            taskPriorityService.create(new TaskPriority(i, "name"+i, (int)i));
//        }
    }
}
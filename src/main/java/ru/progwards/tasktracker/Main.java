package ru.progwards.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.converter.impl.UserDtoConverter;
import ru.progwards.tasktracker.controller.dto.ProjectDtoCreate;
import ru.progwards.tasktracker.controller.dto.UserDto;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.impl.UserService;
import ru.progwards.tasktracker.service.facade.impl.project.ProjectCreateService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.User;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        CreateService<Project> projectCreateService = context.getBean(ProjectCreateService.class);

        Converter<Project, ProjectDtoCreate> converter = context.getBean(ProjectDtoCreateConverter.class);

        Converter<User, UserDto> userDtoConverter = context.getBean(UserDtoConverter.class);

        GetService<Long, User> userGetService = context.getBean(UserService.class);
//
//        for (long i = 0; i < 5; i++) {
//            projectCreateService.create(converter.toModel(new ProjectDtoCreate(i, "name"+i, "desc"+i, "prefix"+i, userDtoConverter.toDto(userGetService.get(i)))));
//        }

//        CreateService<TaskPriority> taskPriorityService = context.getBean(TaskPriorityService.class);
//
//        for (long i = 0; i < 5; i++) {
//            taskPriorityService.create(new TaskPriority(i, "name"+i, (int)i));
//        }
    }
}
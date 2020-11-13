package ru.progwards.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.converter.impl.ProjectDtoConverter;
import ru.progwards.tasktracker.controller.dto.ProjectDto;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.impl.UserService;
import ru.progwards.tasktracker.service.facade.impl.project.ProjectCreateService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.ZonedDateTime;
import java.util.ArrayList;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        CreateService<Project> createService = context.getBean(ProjectCreateService.class);

        Converter<Project, ProjectDto> converter = context.getBean(ProjectDtoConverter.class);

        UserService userService = context.getBean(UserService.class);

        for (long i = 0; i < 5; i++) {
            createService.create(converter.toModel(new ProjectDto(i, "name"+i+i, "desc"+i, "prefix", userService.get(i))));
        }
    }
}

package ru.progwards.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.progwards.tasktracker.service.facade.CreateService;
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

        for (long i = 0; i < 5; i++) {
            createService.create(new Project(i, "name"+i, "description"+i, "prefix", new User(),
                    ZonedDateTime.now(), new WorkFlow(i, "name", true, i, null), new ArrayList<>(), 0L));
        }
    }
}

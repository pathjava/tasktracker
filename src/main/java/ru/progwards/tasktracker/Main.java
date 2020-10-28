package ru.progwards.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

//        ProjectCreateService createService = context.getBean(ProjectCreateService.class);
//
//        for (long i = 0; i < 5; i++) {
//            createService.create(new Project(i, "name"+i, "description"+i, new User(i),
//                    ZonedDateTime.now(), new WorkFlow(i), new ArrayList<>(), 0L));
//        }
    }
}

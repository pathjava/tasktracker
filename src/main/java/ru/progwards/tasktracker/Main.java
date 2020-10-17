package ru.progwards.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;


@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

//        ProjectEntityRepository repository = context.getBean(ProjectEntityRepository.class);
//        for (long i = 0; i < 10; i++) {
//            repository.create(new ProjectEntity(i, "name"+i, "description"+i, i));
//        }
    }
}

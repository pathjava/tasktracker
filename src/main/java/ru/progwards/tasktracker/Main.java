package ru.progwards.tasktracker;

import org.aspectj.lang.JoinPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.progwards.tasktracker.exception.CreateTemplateException;
import ru.progwards.tasktracker.util.CreateDefaultTemplate;
import ru.progwards.tasktracker.util.ImportDump;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        //CreateDefaultTemplate createDefaultTemplate = new CreateDefaultTemplate();
        //createDefaultTemplate.exec();

        // Создание сущностей по шаблону при необходимости
        //context.getBean(CreateDefaultTemplate.class).exec();
    }
}

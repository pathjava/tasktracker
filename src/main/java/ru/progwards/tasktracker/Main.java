package ru.progwards.tasktracker;

import org.aspectj.lang.JoinPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.progwards.tasktracker.exception.CreateTemplateException;
import ru.progwards.tasktracker.util.CreateDefaultTemplate;
import ru.progwards.tasktracker.util.ImportDump;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        CreateDefaultTemplate createDefaultTemplate = new CreateDefaultTemplate();
        createDefaultTemplate.exec();
    }
}

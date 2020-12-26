package ru.progwards.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.progwards.tasktracker.util.CmdExec;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        CmdExec cmdExec = new CmdExec();
        cmdExec.exec1();
    }
}

package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.impl.jsonhandler.ProjectEntityJsonHandler;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

@SpringBootTest
public class ProjectEntityJsonHandlerTest {
    private String content;

    @Autowired
    private ProjectEntityJsonHandler projectEntityJsonHandler;

    // проверка размеров getMap() до записи в файл и после чтения из файла
    @Test
    final void writeReadTest() {
        for (long i = 0; i < 10; i++) {
            projectEntityJsonHandler.addMap(i,new ProjectEntity(i, "name"+i, "description"+i, "", i, 1000L, i, 0L));
        }

        int beforeSize = projectEntityJsonHandler.getMap().size();
        System.out.println(beforeSize);
        projectEntityJsonHandler.write();

        projectEntityJsonHandler.read();
        int afterSize = projectEntityJsonHandler.getMap().size();

        Assertions.assertEquals(beforeSize, afterSize);
    }
}

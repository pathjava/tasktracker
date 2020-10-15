package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

@SpringBootTest
public class JsonHandlerProjectEntityTest {

    @Autowired
    private JsonHandlerProjectEntity jsonHandlerProjectEntity;

    // проверка размеров getMap() до записи в файл и после чтения из файла
    @Test
    public void writeReadTest() {

        System.out.println(jsonHandlerProjectEntity.getMap());

        for (long i = 0; i < 10; i++) {
            ProjectEntity entity = jsonHandlerProjectEntity.map.put(i,new ProjectEntity(i, "name"+i, "description"+i, i));
        }

        int beforeSize = jsonHandlerProjectEntity.getMap().size();
        jsonHandlerProjectEntity.write();

        jsonHandlerProjectEntity.read();
        int afterSize = jsonHandlerProjectEntity.getMap().size();

        Assertions.assertEquals(beforeSize, afterSize);
    }
}

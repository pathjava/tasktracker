package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.nio.file.Files;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class JsonHandlerProjectEntityTest {
    private String content;

    @Autowired
    private JsonHandlerProjectEntity jsonHandlerProjectEntity;

    // проверка размеров getMap() до записи в файл и после чтения из файла
    @Test
    final void writeReadTest() {
//        System.out.println(jsonHandlerProjectEntity.map);
//        System.out.println(jsonHandlerProjectEntity.getMap());
//
//        Mockito.when(jsonHandlerProjectEntity.getMap()).thenReturn(new ConcurrentHashMap<>());
//        jsonHandlerProjectEntity.map.put(null, null);
//        System.out.println(jsonHandlerProjectEntity.map);

        for (long i = 0; i < 10; i++) {
            jsonHandlerProjectEntity.addMap(i,new ProjectEntity(i, "name"+i, "description"+i, "", i, 1000L, i, 0L));
        }

        int beforeSize = jsonHandlerProjectEntity.getMap().size();
        System.out.println(beforeSize);
        jsonHandlerProjectEntity.write();

        jsonHandlerProjectEntity.read();
        int afterSize = jsonHandlerProjectEntity.getMap().size();

        Assertions.assertEquals(beforeSize, afterSize);
    }

    @AfterEach
    void destroy() {
        jsonHandlerProjectEntity.clearRepository();
    }
}

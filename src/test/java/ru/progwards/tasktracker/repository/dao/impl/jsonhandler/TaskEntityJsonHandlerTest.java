package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * тестирование обработчика json задач
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskEntityJsonHandlerTest {

    @Autowired
    private JsonHandler<Long, TaskEntity> jsonHandler;

    @BeforeEach
    public void clearBefore() {
        jsonHandler.getMap().clear();
    }

    @AfterEach
    public void clearAfter() {
        jsonHandler.getMap().clear();
    }

    @Test
    public void testWrite() {
        assertEquals(0, jsonHandler.getMap().size());

        createEntitiesForTest();

        jsonHandler.write();

        assertEquals(2, jsonHandler.getMap().size());
    }

    @Test
    public void testRead() {
        assertEquals(0, jsonHandler.getMap().size());
        createEntitiesForTest();
        jsonHandler.read();
        assertEquals(2, jsonHandler.getMap().size());
    }

    private void createEntitiesForTest() {
        jsonHandler.getMap().put(
                1L, new TaskEntity(null, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false));
        jsonHandler.getMap().put(
                2L, new TaskEntity(null, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );
    }
}
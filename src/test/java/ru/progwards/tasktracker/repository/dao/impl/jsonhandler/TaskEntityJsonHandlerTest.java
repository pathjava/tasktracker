package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

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
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void clear() {
        jsonHandler.getMap().clear();
    }

    @Test
    public void testWrite() {
        int sizeOne = jsonHandler.getMap().size();

        jsonHandler.getMap().put(
                1L, new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false));
        jsonHandler.getMap().put(
                2L, new TaskEntity(2L, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );

        jsonHandler.write();

        int sizeTwo = jsonHandler.getMap().size();

        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }

    @Test
    public void testRead() {
        int sizeOne = jsonHandler.getMap().size();
        jsonHandler.read();
        int sizeTwo = jsonHandler.getMap().size();

        assertNotNull(jsonHandler.getMap());
        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }
}
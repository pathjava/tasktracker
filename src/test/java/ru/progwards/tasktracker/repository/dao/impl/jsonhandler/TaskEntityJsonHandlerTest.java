package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TaskEntityJsonHandlerTest {

    @Autowired
    private JsonHandler<Long, TaskEntity> jsonHandlerTask;

    @BeforeEach
    public void clear() {
        jsonHandlerTask.getMap().clear();
    }

    @Test
    public void testWrite() {
        int sizeOne = jsonHandlerTask.getMap().size();

        jsonHandlerTask.getMap().put(
                1L, new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false));
        jsonHandlerTask.getMap().put(
                2L, new TaskEntity(2L, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );

        jsonHandlerTask.write();

        int sizeTwo = jsonHandlerTask.getMap().size();

        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }

    @Test
    public void testRead() {
        int sizeOne = jsonHandlerTask.getMap().size();
        jsonHandlerTask.read();
        int sizeTwo = jsonHandlerTask.getMap().size();

        assertNotNull(jsonHandlerTask.getMap());
        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }
}
package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class JsonHandlerTaskEntityTest {

    @Autowired
    private JsonHandlerTaskEntity jsonHandlerTask;

    @BeforeEach
    public void clear() {
        jsonHandlerTask.tasks.clear();
    }

    @Test
    public void testWrite() {
        int sizeOne = jsonHandlerTask.tasks.size();

        jsonHandlerTask.tasks.put(
                1L, new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, new Project(11L), new User(11L), new User(11L),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        jsonHandlerTask.tasks.put(
                2L, new TaskEntity(2L, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, TaskPriority.MAJOR, new Project(11L), new User(11L), new User(11L),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );

        jsonHandlerTask.write();

        int sizeTwo = jsonHandlerTask.tasks.size();

        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }

    @Test
    public void testRead() {
        int sizeOne = jsonHandlerTask.tasks.size();
        jsonHandlerTask.read();
        int sizeTwo = jsonHandlerTask.tasks.size();

        assertNotNull(jsonHandlerTask.tasks);
        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }
}
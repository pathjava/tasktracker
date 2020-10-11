package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                1L, new TaskEntity(1L, "Testing_task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));
        jsonHandlerTask.tasks.put(
                2L, new TaskEntity(2L, "Testing_task2_test", "description2", TaskType.EPIC, Priority.BLOCKER,
                003L, 004L,
                ZonedDateTime.now().plusDays(1).toEpochSecond(), ZonedDateTime.now().plusDays(2).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.REVIEW, "new_version",
                123456L, 123456L, 123456L));

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
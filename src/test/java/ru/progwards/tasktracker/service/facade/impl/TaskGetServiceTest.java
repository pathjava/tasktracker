package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.progwards.tasktracker.MainForTest;
import ru.progwards.tasktracker.repository.dao.impl.JsonHandlerTaskEntity;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = MainForTest.class)
public class TaskGetServiceTest {

    @Autowired
    private JsonHandlerTaskEntity jsonHandler;

    @Autowired
    private TaskGetService taskGetService;

    @BeforeEach
    public void reader() {
        jsonHandler.tasks.put(5L, new TaskEntity(5L, "task5", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));
        jsonHandler.write();
        jsonHandler.read();
    }

    @Test
    public void testGet() {
        Task tempTask = taskGetService.get(5L);

        assertEquals("task5", tempTask.getName());
    }
}
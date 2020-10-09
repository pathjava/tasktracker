package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.impl.JsonHandlerTaskEntity;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskRemoveServiceTest {

    @Autowired
    private JsonHandlerTaskEntity jsonHandler;

    @Autowired
    private TaskRemoveService taskRemoveService;

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
    public void testRemove() {
        int sizeOne = jsonHandler.tasks.size();
        Task tempTask = new Task(5L, "task5", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L);

        taskRemoveService.remove(tempTask);

        int sizeTwo = jsonHandler.tasks.size();

        assertEquals(1, sizeOne);
        assertEquals(0, sizeTwo);
    }
}
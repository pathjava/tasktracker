package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.impl.JsonHandlerTaskEntity;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TaskGetListServiceTest {

    @Autowired
    private JsonHandlerTaskEntity jsonHandler;

    @Autowired
    private TaskCreateService taskCreateService;

    @Autowired
    private TaskGetListService taskGetListService;

    @BeforeEach
    public void reader() {
        jsonHandler.tasks.clear();
        taskCreateService.create(
                new Task(1L, "Testing_task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
        );
        taskCreateService.create(
                new Task(2L, "Testing_task2_test", "description2", TaskType.EPIC, Priority.CRITICAL,
                        003L, 003L, ZonedDateTime.now().plusDays(1), ZonedDateTime.now().plusDays(2),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.REVIEW, "new_version",
                        123456L, 123456L, 123456L)
        );
    }

    @Test
    public void testGetList() {
        Collection<Task> tempList = taskGetListService.getList();

        assertNotNull(tempList);
        assertEquals(2, tempList.size());
    }
}
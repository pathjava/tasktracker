package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TaskCreateServiceTest {

    @Mock
    private TaskCreateService taskCreateService;

    @Test
    public void testCreate() {
        taskCreateService.create(
                new Task(1L, "Testing_task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
        );

        verify(taskCreateService, times(1)).create(any());
    }
}
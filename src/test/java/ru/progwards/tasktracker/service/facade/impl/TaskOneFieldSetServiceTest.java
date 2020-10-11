package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.impl.JsonHandlerTaskEntity;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskOneFieldSetServiceTest {

    @Autowired
    private JsonHandlerTaskEntity jsonHandler;

    @Autowired
    private TaskEntityRepository taskRepository;

    @Autowired
    private TaskCreateService taskCreateService;

    @Autowired
    private TaskOneFieldSetService taskOneFieldSetService;

    @BeforeEach
    public void reader() {
        jsonHandler.tasks.clear();
        taskCreateService.create(
                new Task(1L, "Testing_task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
        );
    }

    @Test
    public void testSetOneField() {
        assertEquals("STR_CODE_TTT", taskRepository.get(1L).getStrCode());

        UpdateOneValue tempValue = new UpdateOneValue(1L, "STR_CODE_TTT_New_Value", "strCode");

        taskOneFieldSetService.setOneField(tempValue);

        assertEquals("STR_CODE_TTT_New_Value", taskRepository.get(1L).getStrCode());
    }
}
package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.impl.JsonHandlerTaskEntity;
import ru.progwards.tasktracker.repository.dao.impl.TaskEntityRepository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskOneFieldSetServiceTest {

    @Autowired
    private JsonHandlerTaskEntity jsonHandler;

    @Autowired
    private TaskEntityRepository taskRepository;

    @Autowired
    private TaskOneFieldSetService taskOneFieldSetService;

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
    public void testSetOneField() {
        assertEquals("STR_CODE_TTT", taskRepository.get(5L).getStrCode());

        UpdateOneValue tempValue = new UpdateOneValue(5L, "STR_CODE_TTT_New_Value", "strCode");

        taskOneFieldSetService.setOneField(tempValue);

        assertEquals("STR_CODE_TTT_New_Value", taskRepository.get(5L).getStrCode());
    }
}
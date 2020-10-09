package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.progwards.tasktracker.MainForTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = MainForTest.class)
public class TaskEntityRepositoryTest {

    @Autowired
    private TaskEntityRepository taskRepository;

    @Autowired
    private JsonHandlerTaskEntity jsonHandler;

    @BeforeEach
    public void reader(){
        jsonHandler.tasks.put(1L, new TaskEntity(1L, "task1", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));
        jsonHandler.tasks.put(2L, new TaskEntity(2L, "task2", "description2", TaskType.EPIC, Priority.CRITICAL,
                002L, 007L,
                ZonedDateTime.now().plusDays(1).toEpochSecond(), ZonedDateTime.now().plusDays(3).toEpochSecond(),
                101, 0006L, "STR_CODE_RRR", WorkflowStatus.IN_PROGRESS, "second_version",
                123456L, 123456L, 123456L));
        jsonHandler.write();
        jsonHandler.read();
    }

    @Test
    public void testGetAllTaskEntity() {
        Collection<TaskEntity> tempList = taskRepository.get();

        assertEquals(2, tempList.size());
    }

    @Test
    public void testGetOneTaskEntity() {
        TaskEntity taskEntity = taskRepository.get(2L);

        assertNotNull(taskEntity);
        assertEquals("task2", taskEntity.getName());
    }

    @Test
    public void testCreateTaskEntity() {
        jsonHandler.tasks.clear();
        taskRepository.create(new TaskEntity(3L, "task3", "description3", TaskType.BUG, Priority.MAJOR,
                005L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_BBB", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));
        int sizeTwo = jsonHandler.tasks.size();

        assertEquals(1, sizeTwo);
        assertEquals("task3", taskRepository.get(3L).getName());
    }

    @Test
    public void testUpdateTaskEntity() {
        taskRepository.update(new TaskEntity(1L, "task1_update", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));

        assertEquals("task1_update", taskRepository.get(1L).getName());
    }

    @Test
    public void testDeleteTaskEntity() {
        int sizeOne = jsonHandler.tasks.size();
        taskRepository.delete(1L);
        int sizeTwo = jsonHandler.tasks.size();

        assertEquals(2, sizeOne);
        assertEquals(1, sizeTwo);
    }
}
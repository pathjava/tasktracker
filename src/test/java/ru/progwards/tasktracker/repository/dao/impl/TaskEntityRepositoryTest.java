package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskEntityRepositoryTest {

    @Mock
    private TaskEntityRepository taskRepository;

    @Test
    public void testGetAllTaskEntity() {
        when(taskRepository.get()).thenReturn(Arrays.asList(
                new TaskEntity(1L, "task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L,
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L),
                new TaskEntity(2L, "task2_test", "description2", TaskType.EPIC, Priority.CRITICAL,
                        002L, 007L,
                        ZonedDateTime.now().plusDays(1).toEpochSecond(), ZonedDateTime.now().plusDays(3).toEpochSecond(),
                        101, 0006L, "STR_CODE_RRR", WorkflowStatus.IN_PROGRESS, "second_version",
                        123456L, 123456L, 123456L)
        ));

        Collection<TaskEntity> tempList = taskRepository.get();

        assertEquals(2, tempList.size());
        assertNotNull(tempList);
    }

    @Test
    public void testGetOneTaskEntity() {
        when(taskRepository.get(1L)).thenReturn(
                new TaskEntity(1L, "task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L,
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
        );

        TaskEntity taskEntity = taskRepository.get(1L);

        assertNotNull(taskEntity);
        assertEquals("task1_test", taskEntity.getName());
    }

    @Test
    public void testCreateTaskEntity() {
        TaskEntity task = new TaskEntity(1L, "task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L);

        taskRepository.create(task);

        verify(taskRepository, times(1)).create(task);
    }

    @Test
    public void testUpdateTaskEntity() {
        TaskEntity task = new TaskEntity(1L, "task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L);

        taskRepository.update(task);

        verify(taskRepository, times(1)).update(task);
    }

    @Test
    public void testDeleteTaskEntity() {
        taskRepository.delete(1L);

        verify(taskRepository, times(1)).delete(1L);
    }
}
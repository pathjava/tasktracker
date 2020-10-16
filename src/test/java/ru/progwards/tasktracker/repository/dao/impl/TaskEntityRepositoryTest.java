package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskEntityRepositoryTest {

    @Mock
    private TaskEntityRepository taskRepository;

    @Test
    public void testGetAllTaskEntity() {
        when(taskRepository.get()).thenReturn(Arrays.asList(
                new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, new Project(11L), new User(11L), new User(11L),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                new TaskEntity(2L, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, TaskPriority.MAJOR, new Project(11L), new User(11L), new User(11L),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        ));

        Collection<TaskEntity> tempList = taskRepository.get();

        assertEquals(2, tempList.size());
        assertNotNull(tempList);
    }

    @Test
    public void testGetOneTaskEntity() {
        when(taskRepository.get(anyLong())).thenReturn(
                new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, new Project(11L), new User(11L), new User(11L),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );

        TaskEntity taskEntity = taskRepository.get(1L);

        assertNotNull(taskEntity);
        assertEquals("Test task 1 TEST", taskEntity.getName());
    }

    @Test
    public void testCreateTaskEntity() {
        TaskEntity task = new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                TaskType.BUG, TaskPriority.MAJOR, new Project(11L), new User(11L), new User(11L),
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                new WorkFlowStatus(11L),
                Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        taskRepository.create(task);

        verify(taskRepository, times(1)).create(task);
    }

    @Test
    public void testUpdateTaskEntity() {
        TaskEntity task = new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                TaskType.BUG, TaskPriority.MAJOR, new Project(11L), new User(11L), new User(11L),
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                new WorkFlowStatus(11L),
                Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        taskRepository.update(task);
        verify(taskRepository, times(1)).update(task);
    }

    @Test
    public void testDeleteTaskEntity() {
        taskRepository.delete(1L);

        assertNull(taskRepository.get(1L));
        verify(taskRepository, times(1)).delete(1L);
    }
}
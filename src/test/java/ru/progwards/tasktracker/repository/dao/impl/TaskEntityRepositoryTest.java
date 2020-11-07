package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * тестирование методов работы с репозиторием
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskEntityRepositoryTest {

    @Mock
    private Repository<Long, TaskEntity> repository;

    @Test
    public void testGetAllTaskEntity() {
        when(repository.get()).thenReturn(Arrays.asList(
                new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false),
                new TaskEntity(2L, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        ));

        Collection<TaskEntity> tempList = repository.get();

        assertEquals(2, tempList.size());
        assertNotNull(tempList);
    }

    @Test
    public void testGetAllTaskEntity_Return_Empty_Collection(){
        when(repository.get()).thenReturn(Collections.emptyList());

        Collection<TaskEntity> collection = repository.get();

        assertTrue(collection.isEmpty());
    }

    @Test
    public void testGetOneTaskEntity() {
        when(repository.get(anyLong())).thenReturn(
                new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );

        TaskEntity taskEntity = repository.get(1L);

        assertNotNull(taskEntity);
        assertEquals("Test task 1 TEST", taskEntity.getName());
    }

    @Test
    public void testGetOneTaskEntity_Return_Null(){
        when(repository.get(anyLong())).thenReturn(null);

        TaskEntity taskEntity = repository.get(1L);

        assertNull(taskEntity);
    }

    @Test
    public void testCreateTaskEntity() {
        TaskEntity task = new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                TaskType.BUG, null, 11L, new User(), new User(),
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                null,
                Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);

        repository.create(task);

        verify(repository, times(1)).create(task);
    }

    @Test
    public void testUpdateTaskEntity() {
        TaskEntity task = new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                TaskType.BUG, null, 11L, new User(), new User(),
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                null,
                Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);

        repository.update(task);
        verify(repository, times(1)).update(task);
    }

    @Test
    public void testDeleteTaskEntity() {
        repository.delete(1L);

        assertNull(repository.get(1L));
        verify(repository, times(1)).delete(1L);
    }
}
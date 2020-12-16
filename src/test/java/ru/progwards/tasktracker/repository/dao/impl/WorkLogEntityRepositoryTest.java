package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Тестирование методов репозитория логов
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogEntityRepositoryTest {

    private final List<WorkLogEntity> entities = new ArrayList<>();
    @Mock
    private Repository<Long, WorkLogEntity> repository;
    @Mock
    private RepositoryByTaskId<Long, WorkLogEntity> byTaskId;

    {
        for (int i = 0; i < 3; i++) {
            entities.add(
                    new WorkLogEntity(
                            1L + i, null, null, null, null, "log " + (1 + i)
                    )
            );
        }
    }

    @Test
    void get() {
        when(repository.get(anyLong())).thenReturn(entities.get(0));

        WorkLogEntity entity = repository.get(1L);

        assertNotNull(entity);

        assertThat(entity.getDescription(), equalTo("log 1"));

        verify(repository, times(1)).get(1L);
    }

    @Test
    void get_Return_Null() {
        when(repository.get(anyLong())).thenReturn(null);

        WorkLogEntity entity = repository.get(1L);

        assertNull(entity);

        verify(repository, times(1)).get(1L);
    }

    @Test
    void create() {
        doNothing().when(repository).create(isA(WorkLogEntity.class));

        repository.create(entities.get(0));

        verify(repository, times(1)).create(entities.get(0));
    }

    @Test
    void delete() {
        doNothing().when(repository).delete(anyLong());

        repository.delete(1L);

        verify(repository, times(1)).delete(1L);
    }

    @Test
    void getByTaskId() {
        when(byTaskId.getByTaskId(anyLong())).thenReturn(entities);

        Collection<WorkLogEntity> collection = byTaskId.getByTaskId(1L);

        assertThat(collection.size(), equalTo(3));

        verify(byTaskId, times(1)).getByTaskId(1L);

        assertThat(
                collection.stream()
                        .map(WorkLogEntity::getDescription)
                        .collect(Collectors.toList()),
                containsInAnyOrder("log 1", "log 2", "log 3")
        );
    }

    @Test
    void getByTaskId_Return_Empty_Collection() {
        when(byTaskId.getByTaskId(anyLong())).thenReturn(Collections.emptyList());

        Collection<WorkLogEntity> collection = byTaskId.getByTaskId(1L);

        assertTrue(collection.isEmpty());

        verify(byTaskId, times(1)).getByTaskId(1L);
    }

    @Test
    void update() {
        doNothing().when(repository).update(isA(WorkLogEntity.class));

        repository.update(entities.get(0));

        verify(repository, times(1)).update(entities.get(0));
    }
}
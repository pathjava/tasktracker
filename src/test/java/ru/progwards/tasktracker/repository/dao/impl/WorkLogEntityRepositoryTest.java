package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * тестирование методов репозитория логов
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogEntityRepositoryTest {

    @Mock
    private Repository<Long, WorkLogEntity> repository;

    @Test
    void getOneEntity() {
        when(repository.get(anyLong())).thenReturn(
                new WorkLogEntity(1L, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log 1", null, null)
        );

        WorkLogEntity entity = repository.get(1L);

        assertNotNull(entity);

        assertEquals("Description Log 1", entity.getDescription());
    }

    @Test
    void getOneEntity_Return_Null() {
        when(repository.get(anyLong())).thenReturn(null);

        WorkLogEntity entity = repository.get(1L);

        assertNull(entity);
    }

    @Test
    void create() {
        WorkLogEntity entity = new WorkLogEntity(1L, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                "Description Log 1", null, null);

        repository.create(entity);

        verify(repository, times(1)).create(entity);
    }

    @Test
    void delete() {
        repository.delete(1L);

        assertNull(repository.get(1L));

        verify(repository, times(1)).delete(1L);
    }
}
package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * тестирование создания и удаления связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskEntityRepositoryTest {

    @Mock
    private Repository<Long, RelatedTaskEntity> repository;

    @Test
    void create() {
        repository.create(
                new RelatedTaskEntity(
                        1L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );

        verify(repository, times(1)).create(any(RelatedTaskEntity.class));
    }

    @Test
    void delete() {
        repository.delete(1L);

        assertNull(repository.get(1L));

        verify(repository, times(1)).delete(1L);
    }

    @Test
    void get() {
        when(repository.get(anyLong())).thenReturn(
                new RelatedTaskEntity(1L, new RelationTypeEntity(
                        1L, "блокирующая", new RelationTypeEntity(2L, "блокируемая", null)),
                        1L, 2L)
        );

        RelatedTaskEntity taskEntity = repository.get(1L);

        assertNotNull(taskEntity);

        assertEquals(1, taskEntity.getId());
    }
}
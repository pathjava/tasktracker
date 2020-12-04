package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByAttachedTaskId;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * тестирование создания и удаления связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskEntityRepositoryTest {

    private final List<RelatedTaskEntity> entities = new ArrayList<>();
    @Mock
    private Repository<Long, RelatedTaskEntity> repository;
    @Mock
    private RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId;
    @Mock
    private RepositoryByAttachedTaskId<Long, RelatedTaskEntity> byAttachedTaskId;

    {
        for (int i = 0; i < 3; i++) {
            entities.add(
                    new RelatedTaskEntity(
                            1L + i, null, null, null, false
                    )
            );
        }
    }

    @Test
    void get() {
        when(repository.get(anyLong())).thenReturn(entities.get(0));

        RelatedTaskEntity entity = repository.get(1L);

        assertNotNull(entity);

        verify(repository, times(1)).get(1L);
    }

    @Test
    void get_Return_Null() {
        when(repository.get(anyLong())).thenReturn(null);

        RelatedTaskEntity entity = repository.get(1L);

        assertNull(entity);

        verify(repository, times(1)).get(1L);
    }

    @Test
    void getList() {
        when(repository.get()).thenReturn(entities);

        Collection<RelatedTaskEntity> collection = repository.get();

        assertThat(collection.size(), equalTo(3));

        verify(repository, times(1)).get();
    }

    @Test
    void getList_Return_Empty_Collection() {
        when(repository.get()).thenReturn(Collections.emptyList());

        Collection<RelatedTaskEntity> collection = repository.get();

        assertTrue(collection.isEmpty());

        verify(repository, times(1)).get();
    }

    @Test
    void create() {
        doNothing().when(repository).create(isA(RelatedTaskEntity.class));

        repository.create(entities.get(0));

        verify(repository, times(1)).create(entities.get(0));
    }

    @Test
    void update() {
        doNothing().when(repository).update(isA(RelatedTaskEntity.class));

        repository.update(entities.get(0));

        verify(repository, times(1)).update(entities.get(0));
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

        Collection<RelatedTaskEntity> collection = byTaskId.getByTaskId(1L);

        assertThat(collection.size(), equalTo(3));

        verify(byTaskId, times(1)).getByTaskId(1L);
    }

    @Test
    void getByTaskId_Return_Empty_Collection() {
        when(byTaskId.getByTaskId(anyLong())).thenReturn(Collections.emptyList());

        Collection<RelatedTaskEntity> collection = byTaskId.getByTaskId(1L);

        assertTrue(collection.isEmpty());

        verify(byTaskId, times(1)).getByTaskId(1L);
    }

    @Test
    void getByAttachedTaskId() {
        when(byAttachedTaskId.getByAttachedTaskId(anyLong())).thenReturn(entities);

        Collection<RelatedTaskEntity> collection = byAttachedTaskId.getByAttachedTaskId(1L);

        assertThat(collection.size(), equalTo(3));

        verify(byAttachedTaskId, times(1)).getByAttachedTaskId(1L);
    }

    @Test
    void getByAttachedTaskId_Return_Empty_Collection() {
        when(byAttachedTaskId.getByAttachedTaskId(anyLong())).thenReturn(Collections.emptyList());

        Collection<RelatedTaskEntity> collection = byAttachedTaskId.getByAttachedTaskId(1L);

        assertTrue(collection.isEmpty());

        verify(byAttachedTaskId, times(1)).getByAttachedTaskId(1L);
    }
}
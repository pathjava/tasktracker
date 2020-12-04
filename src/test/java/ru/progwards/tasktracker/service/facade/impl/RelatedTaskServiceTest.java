package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.RelatedTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * тестирование сервиса связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskServiceTest {

    private final List<RelatedTask> valueObjects = new ArrayList<>();
    @Mock
    private CreateService<RelatedTask> createService;
    @Mock
    private GetService<Long, RelatedTask> getService;
    @Mock
    private GetListByTaskService<Long, RelatedTask> getListByTaskService;
    @Mock
    private RemoveService<RelatedTask> removeService;
    @Mock
    private GetListService<RelatedTask> getListService;
    @Mock
    private GetListByAttachedTaskId<Long, RelatedTask> getListByAttachedTaskId;

    {
        for (int i = 0; i < 3; i++) {
            valueObjects.add(new RelatedTask(
                    1L + i, null, 1L + i, null
            ));
        }
    }

    @Test
    void create() {
        doNothing().when(createService).create(isA(RelatedTask.class));

        createService.create(valueObjects.get(0));

        verify(createService, times(1)).create(valueObjects.get(0));
    }

    @Test
    void checkExistTypeAndLink() {
    }

    @Test
    void getListByTaskId() {
        when(getListByTaskService.getListByTaskId(anyLong())).thenReturn(valueObjects);

        Collection<RelatedTask> collection = getListByTaskService.getListByTaskId(1L);

        assertThat(collection.size(), equalTo(3));

        verify(getListByTaskService, times(1)).getListByTaskId(1L);

        assertThat(
                collection.stream()
                        .map(RelatedTask::getId)
                        .collect(Collectors.toList()),
                hasItems(1L, 2L, 3L)
        );
    }

    @Test
    void getListByTaskId_Return_Empty_Collection() {
        when(getListByTaskService.getListByTaskId(anyLong())).thenReturn(Collections.emptyList());

        Collection<RelatedTask> collection = getListByTaskService.getListByTaskId(1L);

        assertTrue(collection.isEmpty());

        verify(getListByTaskService, times(1)).getListByTaskId(1L);
    }

    @Test
    void get() {
        when(getService.get(anyLong())).thenReturn(valueObjects.get(0));

        RelatedTask rt = getService.get(1L);

        assertNotNull(rt);

        verify(getService, times(1)).get(1L);

        assertThat(rt.getId(), equalTo(1L));
    }

    @Test
    void get_Return_Null() {
        when(getService.get(anyLong())).thenReturn(null);

        RelatedTask rt = getService.get(1L);

        assertNull(rt);

        verify(getService, times(1)).get(1L);
    }

    @Test
    void getList() {
        when(getListService.getList()).thenReturn(valueObjects);

        Collection<RelatedTask> collection = getListService.getList();

        assertThat(collection.size(), equalTo(3));

        verify(getListService, times(1)).getList();

        assertThat(
                collection.stream()
                        .map(RelatedTask::getId)
                        .collect(Collectors.toList()),
                hasItems(1L, 2L, 3L)
        );
    }

    @Test
    void getList_Return_Null() {
        when(getListService.getList()).thenReturn(Collections.emptyList());

        Collection<RelatedTask> collection = getListService.getList();

        assertTrue(collection.isEmpty());

        verify(getListService, times(1)).getList();
    }

    @Test
    void remove() {
        doNothing().when(removeService).remove(isA(RelatedTask.class));

        removeService.remove(valueObjects.get(0));

        verify(removeService, times(1)).remove(valueObjects.get(0));
    }

    @Test
    void getListByAttachedTaskId() {
        when(getListByAttachedTaskId.getListByAttachedTaskId(anyLong())).thenReturn(valueObjects);

        Collection<RelatedTask> collection = getListByAttachedTaskId.getListByAttachedTaskId(1L);

        assertThat(collection.size(), equalTo(3));

        verify(getListByAttachedTaskId, times(1)).getListByAttachedTaskId(1L);

        assertThat(
                collection.stream()
                        .map(RelatedTask::getId)
                        .collect(Collectors.toList()),
                hasItems(1L, 2L, 3L)
        );
    }

    @Test
    void getListByAttachedTaskId_Return_Empty_Collection() {
        when(getListByAttachedTaskId.getListByAttachedTaskId(anyLong())).thenReturn(Collections.emptyList());

        Collection<RelatedTask> collection = getListByAttachedTaskId.getListByAttachedTaskId(1L);

        assertTrue(collection.isEmpty());

        verify(getListByAttachedTaskId, times(1)).getListByAttachedTaskId(1L);
    }
}
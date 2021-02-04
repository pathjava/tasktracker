package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ru.progwards.tasktracker.objects.GetModel.getRelatedTaskModel;

/**
 * Тестирование сервиса связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskServiceTest {

    private final List<RelatedTask> relatedTasks = new ArrayList<>();
    @Mock
    private CreateService<RelatedTask> createService;
    @Mock
    private GetService<Long, RelatedTask> getService;
    @Mock
    private RemoveService<RelatedTask> removeService;
    @Mock
    private GetListService<RelatedTask> getListService;

    {
        for (int i = 0; i < 3; i++) {
            RelatedTask relatedTask = getRelatedTaskModel();
            relatedTask.setId(1L + i);
            relatedTasks.add(relatedTask);
        }
    }

    @Test
    void create() {
        doNothing().when(createService).create(isA(RelatedTask.class));

        createService.create(relatedTasks.get(0));

        verify(createService, times(1)).create(relatedTasks.get(0));
    }

    @Test
    void get() {
        when(getService.get(anyLong())).thenReturn(relatedTasks.get(0));

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
        when(getListService.getList()).thenReturn(relatedTasks);

        List<RelatedTask> list = getListService.getList();

        assertThat(list.size(), equalTo(3));

        verify(getListService, times(1)).getList();

        assertThat(
                list.stream()
                        .map(RelatedTask::getId)
                        .collect(Collectors.toList()),
                hasItems(1L, 2L, 3L)
        );
    }

    @Test
    void getList_Return_Null() {
        when(getListService.getList()).thenReturn(Collections.emptyList());

        List<RelatedTask> list = getListService.getList();

        assertTrue(list.isEmpty());

        verify(getListService, times(1)).getList();
    }

    @Test
    void remove() {
        doNothing().when(removeService).remove(isA(RelatedTask.class));

        removeService.remove(relatedTasks.get(0));

        verify(removeService, times(1)).remove(relatedTasks.get(0));
    }
}
package ru.progwards.tasktracker.service.impl;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.UpdateOneValue;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса создания задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskServiceTest {

    private final Task task;
    private final List<Task> tasks = new ArrayList<>();
    private final UpdateOneValue oneValue;

    @Mock
    private GetListService<Task> getListService;
    @Mock
    private GetService<Long, Task> getService;
    @Mock
    private CreateService<Task> createService;
    @Mock
    private RefreshService<Task> refreshService;
    @Mock
    private RemoveService<Task> removeService;
    @Mock
    private UpdateOneFieldService<Task> updateOneFieldService;

    {
        task = new Task(
                1L, "TT-1", null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null, false
        );
    }

    {
        for (int i = 0; i < 3; i++) {
            tasks.add(
                    new Task(
                            1L + i, null, "testTask " + (1 + i), null,
                            null, null, null, null, null,
                            null, null, null, null, null,
                            null, null, null, null, null, null, false
                    )
            );
        }
    }

    {
        oneValue = new UpdateOneValue(
                1L, "Test task 1 UpdateOneFieldService updated", "name"
        );
    }

    @Test
    public void create() {
        doNothing().when(createService).create(isA(Task.class));

        createService.create(task);

        verify(createService, times(1)).create(task);
    }

    @Test
    public void get() {
        when(getService.get(anyLong())).thenReturn(task);

        Task t = getService.get(1L);

        assertThat(t.getCode(), Matchers.equalTo("TT-1"));

        verify(getService, times(1)).get(1L);
    }

    @Test
    void get_Return_Null() {
        when(getService.get(anyLong())).thenReturn(null);

        Task t = getService.get(1L);

        assertNull(t);

        verify(getService, times(1)).get(1L);
    }

    @Test
    public void getList() {
        when(getListService.getList()).thenReturn(tasks);

        List<Task> list = getListService.getList();

        assertNotNull(list);

        assertThat(list.size(), equalTo(3));

        verify(getListService, times(1)).getList();

        assertThat(
                list.stream()
                        .map(Task::getName)
                        .collect(Collectors.toList()),
                containsInAnyOrder("testTask 1", "testTask 2", "testTask 3")
        );
    }

    @Test
    public void getList_Return_Empty_Collection() {
        when(getListService.getList()).thenReturn(Collections.emptyList());

        List<Task> list = getListService.getList();

        assertTrue(list.isEmpty());

        verify(getListService, times(1)).getList();
    }

    @Test
    public void refresh() {
        doNothing().when(refreshService).refresh(isA(Task.class));

        refreshService.refresh(task);

        verify(refreshService, times(1)).refresh(task);
    }

    @Test
    public void remove() {
        doNothing().when(removeService).remove(isA(Task.class));

        removeService.remove(task);

        verify(removeService, times(1)).remove(task);
    }

    @Test
    public void updateOneField() {
        doNothing().when(updateOneFieldService).updateOneField(isA(UpdateOneValue.class));

        updateOneFieldService.updateOneField(oneValue);

        verify(updateOneFieldService, times(1)).updateOneField(oneValue);
    }
}
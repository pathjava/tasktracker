package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса типов задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskTypeServiceTest {

    private final List<TaskType> taskTypes = new ArrayList<>();
    @Mock
    private CreateService<TaskType> createService;
    @Mock
    private GetService<Long, TaskType> getService;
    @Mock
    private RemoveService<TaskType> removeService;
    @Mock
    private RefreshService<TaskType> refreshService;
    @Mock
    private GetListService<TaskType> getListService;

    {
        for (int i = 0; i < 3; i++) {
            taskTypes.add(
                    new TaskType(
                            1L + i, null, new WorkFlow(
                            1L + i, "flow " + (1 + i), false, null, null, null
                    ), "taskType " + (1 + i), null
                    )
            );
        }
    }

    @Test
    void create() {
        doNothing().when(createService).create(isA(TaskType.class));

        createService.create(taskTypes.get(0));

        verify(createService, times(1)).create(taskTypes.get(0));
    }

    @Test
    void get() {
        when(getService.get(anyLong())).thenReturn(taskTypes.get(0));

        TaskType tt = getService.get(1L);

        assertNotNull(tt);

        verify(getService, times(1)).get(1L);

        assertThat(tt.getName(), is("taskType 1"));
    }

    @Test
    void get_Return_Null() {
        when(getService.get(anyLong())).thenReturn(null);

        TaskType tt = getService.get(1L);

        assertNull(tt);

        verify(getService, times(1)).get(1L);
    }

    @Test
    void remove() {
        doNothing().when(removeService).remove(isA(TaskType.class));

        removeService.remove(taskTypes.get(0));

        verify(removeService, times(1)).remove(taskTypes.get(0));
    }

    @Test
    void refresh() {
        doNothing().when(refreshService).refresh(isA(TaskType.class));

        refreshService.refresh(taskTypes.get(0));

        verify(refreshService, times(1)).refresh(taskTypes.get(0));
    }

    @Test
    void getList() {
        when(getListService.getList()).thenReturn(taskTypes);

        List<TaskType> list = getListService.getList();

        assertThat(list.size(), equalTo(3));

        verify(getListService, times(1)).getList();

        assertThat(
                list.stream()
                        .map(TaskType::getName)
                        .collect(Collectors.toList()),
                containsInAnyOrder(
                        "taskType 1", "taskType 2", "taskType 3"
                )
        );
    }

    @Test
    void getList_Return_Empty_Collection() {
        when(getListService.getList()).thenReturn(Collections.emptyList());

        List<TaskType> list = getListService.getList();

        assertTrue(list.isEmpty());

        verify(getListService, times(1)).getList();
    }
}
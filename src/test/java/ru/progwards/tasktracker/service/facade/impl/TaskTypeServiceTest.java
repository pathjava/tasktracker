package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.TaskType;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.util.ArrayList;
import java.util.Collection;
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

    private final List<TaskType> valueObjects = new ArrayList<>();
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
    @Mock
    private GetListByProjectService<Long, TaskType> getListByProjectService;
    @Autowired
    private TaskTypeService taskTypeService;

    {
        for (int i = 0; i < 3; i++) {
            valueObjects.add(
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

        createService.create(valueObjects.get(0));

        verify(createService, times(1)).create(valueObjects.get(0));
    }

    @Test
    void getTemplateWorkFlow() {
        String workFlowName = "flow 1";
        String typeName = "BUG";

        WorkFlow wf = taskTypeService.getTemplateWorkFlow(workFlowName, typeName);

        assertNull(wf.getId());
        assertThat(wf.getName(), equalTo("flow 1 - TaskType BUG"));
        assertFalse(wf.isPattern());
        assertNull(wf.getStartStatus());
        assertNull(wf.getStatuses());
    }

    @Test
    void get() {
        when(getService.get(anyLong())).thenReturn(valueObjects.get(0));

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

        removeService.remove(valueObjects.get(0));

        verify(removeService, times(1)).remove(valueObjects.get(0));
    }

    @Test
    void refresh() {
        doNothing().when(refreshService).refresh(isA(TaskType.class));

        refreshService.refresh(valueObjects.get(0));

        verify(refreshService, times(1)).refresh(valueObjects.get(0));
    }

    @Test
    void getListByProjectId() {
        when(getListByProjectService.getListByProjectId(anyLong())).thenReturn(valueObjects);

        Collection<TaskType> collection = getListByProjectService.getListByProjectId(10L);

        assertThat(collection.size(), equalTo(3));

        verify(getListByProjectService, times(1)).getListByProjectId(10L);

        assertThat(
                collection.stream()
                        .map(TaskType::getName)
                        .collect(Collectors.toList()),
                containsInAnyOrder(
                        "taskType 1", "taskType 2", "taskType 3"
                )
        );
    }

    @Test
    void getListByProjectId_Return_Empty_Collection() {
        when(getListByProjectService.getListByProjectId(anyLong())).thenReturn(Collections.emptyList());

        Collection<TaskType> collection = getListByProjectService.getListByProjectId(10L);

        assertTrue(collection.isEmpty());

        verify(getListByProjectService, times(1)).getListByProjectId(10L);
    }

    @Test
    void getList() {
        when(getListService.getList()).thenReturn(valueObjects);

        Collection<TaskType> collection = getListService.getList();

        assertThat(collection.size(), equalTo(3));

        verify(getListService, times(1)).getList();

        assertThat(
                collection.stream()
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

        Collection<TaskType> collection = getListService.getList();

        assertTrue(collection.isEmpty());

        verify(getListService, times(1)).getList();
    }
}
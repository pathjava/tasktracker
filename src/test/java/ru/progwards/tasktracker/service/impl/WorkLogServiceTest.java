package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.model.WorkLog;
import ru.progwards.tasktracker.model.types.EstimateChange;
import ru.progwards.tasktracker.service.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса создания лога (Журнала работ)
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogServiceTest {

    private final List<WorkLog> workLogs = new ArrayList<>();
    @Mock
    private CreateService<WorkLog> createService;
    @Mock
    private GetService<Long, WorkLog> getService;
    @Mock
    private GetListService<WorkLog> getListService;
    @Mock
    private RefreshService<WorkLog> refreshService;
    @Mock
    private RemoveService<WorkLog> removeService;

    {
        User user = mock(User.class);
        for (int i = 0; i < 3; i++) {
            workLogs.add(
                    new WorkLog(
                            1L + i,
                            null,
                            Duration.ofHours(15),
                            user,
                            ZonedDateTime.now(),
                            "WorkLog description " + (1 + i),
                            setEstimateValue(i),
                            Duration.ofHours(40)
                    )
            );
        }
    }

    public EstimateChange setEstimateValue(int i) {
        switch (i) {
            case 0:
                return EstimateChange.AUTO_REDUCE;
            case 1:
                return EstimateChange.SET_TO_VALUE;
            case 2:
                return EstimateChange.REDUCE_BY_VALUE;
            case 3:
                return EstimateChange.DONT_CHANGE;
            case 4:
                return EstimateChange.INCREASE_BY_VALUE;
        }
        return null;
    }

    @Test
    void create() {
        doNothing().when(createService).create(isA(WorkLog.class));

        createService.create(workLogs.get(0));

        verify(createService, times(1)).create(workLogs.get(0));
    }

    @Test
    void get() {
        when(getService.get(anyLong())).thenReturn(workLogs.get(0));

        WorkLog wl = getService.get(1L);

        assertNotNull(wl);

        verify(getService, times(1)).get(1L);

        assertThat(wl.getDescription(), is("WorkLog description 1"));
    }

    @Test
    void get_Return_Null() {
        when(getService.get(anyLong())).thenReturn(null);

        WorkLog wl = getService.get(1L);

        assertNull(wl);

        verify(getService, times(1)).get(1L);
    }

    @Test
    void refresh() {
        doNothing().when(refreshService).refresh(workLogs.get(0));

        refreshService.refresh(workLogs.get(0));

        verify(refreshService, times(1)).refresh(workLogs.get(0));
    }

    @Test
    void remove() {
        doNothing().when(removeService).remove(workLogs.get(0));

        removeService.remove(workLogs.get(0));

        verify(removeService, times(1)).remove(workLogs.get(0));
    }

    @Test
    void getList() {
        when(getListService.getList()).thenReturn(workLogs);

        List<WorkLog> list = getListService.getList();

        assertNotNull(list);

        assertThat(list.size(), equalTo(3));

        verify(getListService, times(1)).getList();

        assertThat(
                list.stream()
                        .map(WorkLog::getDescription)
                        .collect(Collectors.toList()),
                containsInAnyOrder("WorkLog description 1", "WorkLog description 2", "WorkLog description 3")
        );
    }

    @Test
    public void getList_Return_Empty_Collection() {
        when(getListService.getList()).thenReturn(Collections.emptyList());

        List<WorkLog> list = getListService.getList();

        assertTrue(list.isEmpty());

        verify(getListService, times(1)).getList();
    }
}
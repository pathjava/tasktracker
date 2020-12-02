package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkLog;
import ru.progwards.tasktracker.util.types.EstimateChange;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.*;

/**
 * тестирование сервиса создания лога
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WorkLogServiceTest {

    private final List<WorkLog> workLog = new ArrayList<>();
    @Mock
    private CreateService<WorkLog> createService;
    @Mock
    private GetListByTaskService<Long, WorkLog> getListByTaskService;
    @Mock
    private GetService<Long, WorkLog> getService;
    @Mock
    private RefreshService<WorkLog> refreshService;
    @Mock
    private RemoveService<WorkLog> removeService;
    @Autowired
    private WorkLogService workLogService;

    {
        User user = mock(User.class);
        for (int i = 0; i < 5; i++) {
            workLog.add(
                    new WorkLog(
                            1L + i,
                            1L,
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

    public Task createTestTask() {
        return new Task(
                1L, null, null, null,
                null, null, null, null, null,
                null, null, null, null,
                Duration.ofHours(50), Duration.ofHours(0), Duration.ofHours(50),
                null, null, null
        );
    }

    @Test
    void create() {
        doNothing().when(createService).create(isA(WorkLog.class));

        createService.create(workLog.get(0));

        verify(createService, times(1)).create(workLog.get(0));
    }

    @Test
    void logCreateEstimateChange_AUTO_REDUCE() {
        Task updatedTask = workLogService.logCreateEstimateChange(workLog.get(0), createTestTask());

        assertThat(updatedTask.getTimeSpent(), equalTo(Duration.ofHours(15)));
        assertThat(updatedTask.getTimeLeft(), equalTo(Duration.ofHours(35)));
    }

    @Test
    void logCreateEstimateChange_SET_TO_VALUE() {
        Task updatedTask = workLogService.logCreateEstimateChange(workLog.get(1), createTestTask());

        assertThat(updatedTask.getTimeSpent(), equalTo(Duration.ofHours(15)));
        assertThat(updatedTask.getTimeLeft(), equalTo(Duration.ofHours(40)));
    }

    @Test
    void logCreateEstimateChange_REDUCE_BY_VALUE() {
        Task updatedTask = workLogService.logCreateEstimateChange(workLog.get(2), createTestTask());

        assertThat(updatedTask.getTimeSpent(), equalTo(Duration.ofHours(15)));
        assertThat(updatedTask.getTimeLeft(), equalTo(Duration.ofHours(10)));
    }

    @Test
    void logCreateEstimateChange_BREAK_to_DONT_CHANGE_and_INCREASE_BY_VALUE() {
        Task task_DONT_CHANGE = workLogService.logCreateEstimateChange(workLog.get(3), createTestTask());

        assertThat(task_DONT_CHANGE.getTimeSpent(), equalTo(Duration.ofHours(15)));
        assertThat(task_DONT_CHANGE.getTimeLeft(), equalTo(Duration.ofHours(50)));

        Task task_INCREASE_BY_VALUE = workLogService.logCreateEstimateChange(workLog.get(4), createTestTask());

        assertThat(task_INCREASE_BY_VALUE.getTimeSpent(), equalTo(Duration.ofHours(15)));
        assertThat(task_INCREASE_BY_VALUE.getTimeLeft(), equalTo(Duration.ofHours(50)));
    }

    @Test
    void getListByTaskId() {
        when(getListByTaskService.getListByTaskId(anyLong())).thenReturn(workLog);

        Collection<WorkLog> collection = getListByTaskService.getListByTaskId(1L);

        assertThat(collection.size(), equalTo(5));

        verify(getListByTaskService, times(1)).getListByTaskId(1L);

        assertThat(
                collection.stream()
                        .map(WorkLog::getDescription)
                        .collect(Collectors.toList()),
                containsInAnyOrder(
                        "WorkLog description 1",
                        "WorkLog description 2",
                        "WorkLog description 3",
                        "WorkLog description 4",
                        "WorkLog description 5"
                )
        );
    }

    @Test
    void getListByTaskId_Return_Empty_Collection() {
        when(getListByTaskService.getListByTaskId(anyLong())).thenReturn(Collections.emptyList());

        Collection<WorkLog> collection = getListByTaskService.getListByTaskId(1L);

        assertTrue(collection.isEmpty());

        verify(getListByTaskService, times(1)).getListByTaskId(1L);
    }

    @Test
    void get() {
        when(getService.get(anyLong())).thenReturn(workLog.get(0));

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
        doNothing().when(refreshService).refresh(workLog.get(0));

        refreshService.refresh(workLog.get(0));

        verify(refreshService, times(1)).refresh(workLog.get(0));
    }

    @Test
    void logRefreshEstimateChange_AUTO_REDUCE() {
        Task task = createTestTask();
        task.setTimeSpent(Duration.ofHours(20));
        Duration spentEarlier = Duration.ofHours(10);

        Task updatedTask = workLogService.logRefreshEstimateChange(workLog.get(0), spentEarlier, task);

        assertThat(updatedTask.getTimeSpent(), equalTo(Duration.ofHours(25)));
        assertThat(updatedTask.getTimeLeft(), equalTo(Duration.ofHours(45)));
    }

    @Test
    void logRefreshEstimateChange_SET_TO_VALUE() {
        Task task = createTestTask();
        task.setTimeSpent(Duration.ofHours(20));
        Duration spentEarlier = Duration.ofHours(10);

        Task updatedTask = workLogService.logRefreshEstimateChange(workLog.get(1), spentEarlier, task);

        assertThat(updatedTask.getTimeSpent(), equalTo(Duration.ofHours(25)));
        assertThat(updatedTask.getTimeLeft(), equalTo(Duration.ofHours(40)));
    }

    @Test
    void logRefreshEstimateChange_BREAK_to_DONT_CHANGE_and_REDUCE_BY_VALUE_and_INCREASE_BY_VALUE() {
        Duration spentEarlier = Duration.ofHours(10);
        Task task = createTestTask();

        task.setTimeSpent(Duration.ofHours(20));
        Task updatedTask_DONT_CHANGE = workLogService.logRefreshEstimateChange(
                workLog.get(3), spentEarlier, task
        );
        assertThat(updatedTask_DONT_CHANGE.getTimeSpent(), equalTo(Duration.ofHours(25)));
        assertThat(updatedTask_DONT_CHANGE.getTimeLeft(), equalTo(Duration.ofHours(50)));

        task.setTimeSpent(Duration.ofHours(20));
        Task updatedTask_REDUCE_BY_VALUE = workLogService.logRefreshEstimateChange(
                workLog.get(2), spentEarlier, task
        );
        assertThat(updatedTask_REDUCE_BY_VALUE.getTimeSpent(), equalTo(Duration.ofHours(25)));
        assertThat(updatedTask_REDUCE_BY_VALUE.getTimeLeft(), equalTo(Duration.ofHours(50)));

        task.setTimeSpent(Duration.ofHours(20));
        Task updatedTask_INCREASE_BY_VALUE = workLogService.logRefreshEstimateChange(
                workLog.get(4), spentEarlier, task
        );
        assertThat(updatedTask_INCREASE_BY_VALUE.getTimeSpent(), equalTo(Duration.ofHours(25)));
        assertThat(updatedTask_INCREASE_BY_VALUE.getTimeLeft(), equalTo(Duration.ofHours(50)));
    }

    @Test
    void remove() {
        doNothing().when(removeService).remove(workLog.get(0));

        removeService.remove(workLog.get(0));

        verify(removeService, times(1)).remove(workLog.get(0));
    }

    @Test
    void logRemoveEstimateChange_AUTO_REDUCE() {
        Duration spentEarlier = Duration.ofHours(10);
        Task task = createTestTask();
        task.setTimeSpent(Duration.ofHours(20));

        Task updatedTask = workLogService.logRemoveEstimateChange(workLog.get(0), spentEarlier, task);

        assertThat(updatedTask.getTimeSpent(), equalTo(Duration.ofHours(10)));
        assertThat(updatedTask.getTimeLeft(), equalTo(Duration.ofHours(40)));
    }

    @Test
    void logRemoveEstimateChange_SET_TO_VALUE() {
        Duration spentEarlier = Duration.ofHours(10);
        Task task = createTestTask();
        task.setTimeSpent(Duration.ofHours(20));

        Task updatedTask = workLogService.logRemoveEstimateChange(workLog.get(1), spentEarlier, task);

        assertThat(updatedTask.getTimeSpent(), equalTo(Duration.ofHours(10)));
        assertThat(updatedTask.getTimeLeft(), equalTo(Duration.ofHours(40)));
    }

    @Test
    void logRemoveEstimateChange_INCREASE_BY_VALUE() {
        Duration spentEarlier = Duration.ofHours(10);
        Task task = createTestTask();
        task.setTimeSpent(Duration.ofHours(20));

        Task updatedTask = workLogService.logRemoveEstimateChange(workLog.get(4), spentEarlier, task);

        assertThat(updatedTask.getTimeSpent(), equalTo(Duration.ofHours(10)));
        assertThat(updatedTask.getTimeLeft(), equalTo(Duration.ofHours(90)));
    }

    @Test
    void logRemoveEstimateChange_BREAK_to_DONT_CHANGE_and_REDUCE_BY_VALUE() {
        Duration spentEarlier = Duration.ofHours(10);
        Task task = createTestTask();

        task.setTimeSpent(Duration.ofHours(20));
        Task updatedTask_DONT_CHANGE = workLogService.logRefreshEstimateChange(
                workLog.get(3), spentEarlier, task
        );
        assertThat(updatedTask_DONT_CHANGE.getTimeSpent(), equalTo(Duration.ofHours(25)));
        assertThat(updatedTask_DONT_CHANGE.getTimeLeft(), equalTo(Duration.ofHours(50)));

        task.setTimeSpent(Duration.ofHours(20));
        Task updatedTask_REDUCE_BY_VALUE = workLogService.logRefreshEstimateChange(
                workLog.get(2), spentEarlier, task
        );
        assertThat(updatedTask_REDUCE_BY_VALUE.getTimeSpent(), equalTo(Duration.ofHours(25)));
        assertThat(updatedTask_REDUCE_BY_VALUE.getTimeLeft(), equalTo(Duration.ofHours(50)));
    }
}
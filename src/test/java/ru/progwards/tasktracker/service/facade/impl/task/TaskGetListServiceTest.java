package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.impl.task.TaskGetListService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class TaskGetListServiceTest {

    @Mock
    private TaskGetListService taskGetListService;

    @Test
    public void testGetList() {
        when(taskGetListService.getList()).thenReturn(Arrays.asList(
                new Task(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                new Task(2L, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        ));

        Collection<Task> tempList = taskGetListService.getList();

        assertNotNull(tempList);

        assertThat(tempList.size(), is(2));

        List<String> actualTaskName = tempList.stream()
                .map(Task::getName)
                .collect(Collectors.toUnmodifiableList());

        assertThat(actualTaskName, containsInAnyOrder("Test task 1 TEST", "Test task 2 TEST"));
    }
}
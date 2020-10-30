package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskGetListServiceTest {

    @Mock
    private GetListService<Task> taskGetListService;

    @Test
    public void testGetList() {
        when(taskGetListService.getList()).thenReturn(Arrays.asList(
                new Task(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                new Task(2L, "TT2-2", "Test task 2 TEST", "Description task 2",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
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

    @Test
    public void testGetList_Return_Empty_Collection(){
        when(taskGetListService.getList()).thenReturn(Collections.emptyList());

        Collection<Task> collection = taskGetListService.getList();

        assertTrue(collection.isEmpty());
    }
}
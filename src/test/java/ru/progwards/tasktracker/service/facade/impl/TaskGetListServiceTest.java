package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskGetListServiceTest {

    @Mock
    private TaskGetListService taskGetListService;

    @Test
    public void testGetList() {
        when(taskGetListService.getList()).thenReturn(Arrays.asList(
                new Task(1L, "Testing_task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L),
                new Task(2L, "Testing_task2_test", "description2", TaskType.EPIC, Priority.CRITICAL,
                        003L, 003L, ZonedDateTime.now().plusDays(1), ZonedDateTime.now().plusDays(2),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.REVIEW, "new_version",
                        123456L, 123456L, 123456L)
        ));

        Collection<Task> tempList = taskGetListService.getList();

        assertNotNull(tempList);

        assertThat(tempList.size(), is(2));

        List<String> actualTaskName = tempList.stream()
                .map(Task::getName)
                .collect(Collectors.toUnmodifiableList());

        assertThat(actualTaskName, containsInAnyOrder("Testing_task1_test", "Testing_task2_test"));
    }
}
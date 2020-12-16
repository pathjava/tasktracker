package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса получения списка задач
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskGetListServiceTest {

    private final List<Task> valueObjects = new ArrayList<>();
    @Mock
    private GetListService<Task> getListService;

    {
        for (int i = 0; i < 3; i++) {
            valueObjects.add(
                    new Task(
                            1L + i, null, "testTask " + (1 + i), null,
                            null, null, null, null, null,
                            null, null, null, null, null, null,
                            null, null, null, null, null, null
                    )
            );
        }
    }

    @Test
    public void getList() {
        when(getListService.getList()).thenReturn(valueObjects);

        Collection<Task> collection = getListService.getList();

        assertNotNull(collection);

        assertThat(collection.size(), equalTo(3));

        verify(getListService, times(1)).getList();

        assertThat(
                collection.stream()
                        .map(Task::getName)
                        .collect(Collectors.toList()),
                containsInAnyOrder("testTask 1", "testTask 2", "testTask 3")
        );
    }

    @Test
    public void getList_Return_Empty_Collection() {
        when(getListService.getList()).thenReturn(Collections.emptyList());

        Collection<Task> collection = getListService.getList();

        assertTrue(collection.isEmpty());

        verify(getListService, times(1)).getList();
    }
}
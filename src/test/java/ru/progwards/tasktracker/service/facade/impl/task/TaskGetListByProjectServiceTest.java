package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetListByProjectService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Тестирование получения списка задач по идентификатору проекта
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskGetListByProjectServiceTest {

    private final List<Task> valueObjects = new ArrayList<>();
    @Mock
    private GetListByProjectService<Long, Task> getListByProjectId;

    {
        for (int i = 0; i < 3; i++) {
            valueObjects.add(
                    new Task(
                            1L + i, null, "testTask " + (1 + i), null, null, null,
                            null, null, null, null, null, null, null,
                            null, null, null, null, null, null, null
                    )
            );
        }
    }

    @Test
    void getListByProjectId() {
        when(getListByProjectId.getListByProjectId(anyLong())).thenReturn(valueObjects);

        Collection<Task> collection = getListByProjectId.getListByProjectId(1L);

        assertNotNull(collection);

        assertThat(collection.size(), equalTo(3));

        verify(getListByProjectId, times(1)).getListByProjectId(1L);

        assertThat(
                collection.stream()
                        .map(Task::getName)
                        .collect(Collectors.toList()),
                containsInAnyOrder("testTask 1", "testTask 2", "testTask 3")
        );
    }

    @Test
    void getListByProjectId_Return_Empty_Collection() {
        when(getListByProjectId.getListByProjectId(anyLong())).thenReturn(Collections.emptyList());

        Collection<Task> collection = getListByProjectId.getListByProjectId(1L);

        assertTrue(collection.isEmpty());

        verify(getListByProjectId, times(1)).getListByProjectId(1L);
    }
}
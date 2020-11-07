package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetListByProjectService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * тестирование сервиса получения списка логов задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogGetListByTaskServiceTest {

    @Mock
    private GetListByProjectService<Long, WorkLog> service;

    @Test
    void getListByTaskId() {
        when(service.getListByProjectId(anyLong())).thenReturn(Arrays.asList(
                new WorkLog(
                        1L, 2L, null, null, ZonedDateTime.now(),
                        "Description Log 1", null, null
                ),
                new WorkLog(
                        2L, 2L, null, null, ZonedDateTime.now(),
                        "Description Log 2", null, null
                )
        ));

        Collection<WorkLog> collection = service.getListByProjectId(2L);

        assertNotNull(collection);

        assertThat(collection.size(), is(2));

        List<String> actualDescriptionName = collection.stream()
                .map(WorkLog::getDescription)
                .collect(Collectors.toUnmodifiableList());

        assertThat(actualDescriptionName, containsInAnyOrder("Description Log 1", "Description Log 2"));
    }

    @Test
    void getListByTaskId_Return_Empty_Collection() {
        when(service.getListByProjectId(anyLong())).thenReturn(Collections.emptyList());

        Collection<WorkLog> collection = service.getListByProjectId(2L);

        assertTrue(collection.isEmpty());
    }
}
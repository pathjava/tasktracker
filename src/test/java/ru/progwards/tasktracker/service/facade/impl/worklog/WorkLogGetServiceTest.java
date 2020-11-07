package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
/**
 * тестирование сервиса получения одного лога
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogGetServiceTest {

    @Mock
    private GetService<Long, WorkLog> service;

    @Test
    void get() {
        when(service.get(anyLong())).thenReturn(
                new WorkLog(
                        1L, 2L, null, null, ZonedDateTime.now(),
                        "Description Log 1", null, null
                )
        );

        WorkLog workLog = service.get(1L);

        assertNotNull(workLog);

        assertThat(workLog.getDescription(), equalTo("Description Log 1"));
    }
}
package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * тестирование сервиса удаления лога
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogRemoveServiceTest {

    @Mock
    private RemoveService<WorkLog> service;

    @Test
    void remove() {
        service.remove(
                new WorkLog(
                        1L, 2L, null, null, ZonedDateTime.now(),
                        "Description Log 1", null, null
                )
        );

        verify(service, times(1)).remove(any(WorkLog.class));
    }
}
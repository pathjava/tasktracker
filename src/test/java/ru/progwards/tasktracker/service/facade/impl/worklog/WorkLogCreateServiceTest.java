package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * тестирование сервиса создания лога
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogCreateServiceTest {

    @Mock
    private CreateService<WorkLog> service;

    @Test
    void create() {
        service.create(
                new WorkLog(
                        1L, 2L, null, null, ZonedDateTime.now(),
                        "Description Log 1", null, null
                )
        );

        verify(service, times(1)).create(any(WorkLog.class));
    }
}
package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование сервиса получения одного лога
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogGetServiceTest {

    @Autowired
    private GetService<Long, WorkLog> getService;

    @Autowired
    private CreateService<WorkLog> createService;

    @Autowired
    private GetListByTaskService<Long, WorkLog> byTaskService;

    @Autowired
    private RemoveService<WorkLog> removeService;

    @BeforeEach
    void before() {
        createService.create(
                new WorkLog(
                        null, 2L, null, null, ZonedDateTime.now(),
                        "Description Log GetService", null, null
                )
        );
    }

    @Test
    void get() {
        Long id = byTaskService.getListByTaskId(2L).stream()
                .filter(e -> e.getDescription().equals("Description Log GetService")).findFirst()
                .map(WorkLog::getId)
                .orElse(null);

        if (id != null) {
            WorkLog workLog = getService.get(id);

            assertNotNull(workLog);

            assertThat(workLog.getDescription(), equalTo("Description Log GetService"));

            removeService.remove(workLog);
        } else
            fail();
    }

    @Test
    void get_Return_Null() {
        WorkLog workLog = getService.get(anyLong());

        assertNull(workLog);
    }
}
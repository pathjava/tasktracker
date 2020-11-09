package ru.progwards.tasktracker.service.facade.impl.worklog;

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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * тестирование сервиса создания лога
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogCreateServiceTest {

    @Autowired
    private CreateService<WorkLog> createService;

    @Autowired
    private GetListByTaskService<Long, WorkLog> byTaskService;

    @Autowired
    private RemoveService<WorkLog> removeService;

    @Autowired
    private GetService<Long, WorkLog> getService;

    @Test
    void create() {
        createService.create(
                new WorkLog(
                        null, 2L, null, null, ZonedDateTime.now(),
                        "Description Log CreateService", null, null
                )
        );

        Long id = byTaskService.getListByTaskId(2L).stream()
                .filter(e -> e.getDescription().equals("Description Log CreateService")).findFirst()
                .map(WorkLog::getId)
                .orElse(null);

        if (id != null) {
            WorkLog workLog = getService.get(id);

            assertNotNull(workLog);

            assertThat(workLog.getDescription(), equalTo("Description Log CreateService"));

            removeService.remove(workLog);
        } else
            fail();
    }
}
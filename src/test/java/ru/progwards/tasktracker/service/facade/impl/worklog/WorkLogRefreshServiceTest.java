package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * тестирование обновления лога
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogRefreshServiceTest {

    @Autowired
    private CreateService<WorkLog> createService;

    @Autowired
    private GetListByTaskService<Long, WorkLog> byTaskService;

    @Autowired
    private RefreshService<WorkLog> refreshService;

    @Autowired
    private RemoveService<WorkLog> removeService;

    @Autowired
    private GetService<Long, WorkLog> getService;

    @BeforeEach
    void before(){
        createService.create(
                new WorkLog(
                        null, 2L, null, null, ZonedDateTime.now(),
                        "Description Log RefreshService", null, null
                )
        );
    }

    @Test
    void refresh() {
        Long id = byTaskService.getListByTaskId(2L).stream()
                .filter(e -> e.getDescription().equals("Description Log RefreshService")).findFirst()
                .map(WorkLog::getId)
                .orElse(null);

        if (id != null){
            refreshService.refresh(
                    new WorkLog(
                            id, 2L, null, null, ZonedDateTime.now(),
                            "Description Log RefreshService Updated", null, null
                    )
            );
            WorkLog workLog = getService.get(id);

            assertThat(workLog.getDescription(), equalTo("Description Log RefreshService Updated"));

            removeService.remove(workLog);
        } else
            fail();
    }
}
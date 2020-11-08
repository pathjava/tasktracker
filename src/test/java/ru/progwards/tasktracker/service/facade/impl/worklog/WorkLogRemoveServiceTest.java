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

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * тестирование сервиса удаления лога
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogRemoveServiceTest {

    @Autowired
    private RemoveService<WorkLog> removeService;

    @Autowired
    private CreateService<WorkLog> createService;

    @Autowired
    private GetListByTaskService<Long, WorkLog> byTaskService;

    @Autowired
    private GetService<Long, WorkLog> getService;

    @BeforeEach
    void before() {
        createService.create(
                new WorkLog(
                        null, 2L, null, null, ZonedDateTime.now(),
                        "Description Log RemoveService", null, null
                )
        );
    }

    @Test
    void remove() {
        Long id = byTaskService.getListByTaskId(2L).stream()
                .filter(e -> e.getDescription().equals("Description Log RemoveService")).findFirst()
                .map(WorkLog::getId)
                .orElse(null);

        removeService.remove(
                new WorkLog(
                        id, 2L, null, null, ZonedDateTime.now(),
                        "Description Log RemoveService", null, null
                )
        );

        WorkLog workLog = getService.get(id);

        assertNull(workLog);
    }
}
package ru.progwards.tasktracker.service.facade.impl.worklog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование сервиса получения списка логов задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogGetListByTaskServiceTest {

    @Autowired
    private GetListByTaskService<Long, WorkLog> byTaskService;

    @Autowired
    private CreateService<WorkLog> createService;

    @Autowired
    private JsonHandler<Long, WorkLogEntity> jsonHandler;

    @BeforeEach
    void before() {
        jsonHandler.getMap().clear();

        createService.create(
                new WorkLog(
                        null, 2L, null, null, ZonedDateTime.now(),
                        "Description Log GetListByTaskService 1", null, null
                )
        );
        createService.create(
                new WorkLog(
                        null, 2L, null, null, ZonedDateTime.now(),
                        "Description Log GetListByTaskService 2", null, null
                )
        );
    }

    @Test
    void getListByTaskId() {
        Collection<WorkLog> collection = byTaskService.getListByTaskId(2L);

        assertNotNull(collection);

        assertThat(collection.size(), is(2));

        List<String> actualDescriptionNames = collection.stream()
                .map(WorkLog::getDescription)
                .collect(Collectors.toUnmodifiableList());

        assertThat(actualDescriptionNames, containsInAnyOrder("Description Log GetListByTaskService 1",
                "Description Log GetListByTaskService 2"));
    }

    @Test
    void getListByTaskId_Return_Empty_Collection() {
        jsonHandler.getMap().clear();

        Collection<WorkLog> collection = byTaskService.getListByTaskId(anyLong());

        assertTrue(collection.isEmpty());
    }
}
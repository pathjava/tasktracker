package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByProjectService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
 * тестирование получения списка задач по идентификатору проекта
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskGetListByProjectServiceTest {

    @Autowired
    private GetListByProjectService<Long, Task> listByProjectService;

    @Autowired
    private CreateService<Task> createService;

    @Autowired
    private JsonHandler<Long, TaskEntity> jsonHandler;

    @BeforeEach
    void before() {
        jsonHandler.getMap().clear();

        createService.create(
                new Task(null, "TT1-1", "Test GetListByProjectService 1", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
        createService.create(
                new Task(null, "TT1-2", "Test GetListByProjectService 2", "Description task 2",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
    }

    @Test
    void getListByProjectId() {
        Collection<Task> collection = listByProjectService.getListByProjectId(11L);

        assertNotNull(collection);

        assertThat(collection.size(), is(2));

        List<String> actualTaskName = collection.stream()
                .map(Task::getName)
                .collect(Collectors.toUnmodifiableList());

        assertThat(actualTaskName, containsInAnyOrder("Test GetListByProjectService 1",
                "Test GetListByProjectService 2"));
    }

    @Test
    void getListByProjectId_Return_Empty_Collection() {
        jsonHandler.getMap().clear();

        Collection<Task> collection = listByProjectService.getListByProjectId(anyLong());

        assertTrue(collection.isEmpty());
    }
}
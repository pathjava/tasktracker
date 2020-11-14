package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListService;
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

/**
 * тестирование сервиса получения списка задач
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskGetListServiceTest {

    @Autowired
    private GetListService<Task> getListService;

    @Autowired
    private CreateService<Task> createService;

    @Autowired
    private JsonHandler<Long, TaskEntity> jsonHandler;

    @BeforeEach
    void before() {
        jsonHandler.getMap().clear();

        createService.create(
                new Task(null, "TT1-1", "Test GetListService 1", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
        createService.create(
                new Task(null, "TT1-2", "Test GetListService 2", "Description task 2",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
    }

    @Test
    public void getList() {
        Collection<Task> collection = getListService.getList();

        assertNotNull(collection);

        assertThat(collection.size(), is(2));

        List<String> actualTasksNames = collection.stream()
                .map(Task::getName)
                .collect(Collectors.toUnmodifiableList());

        assertThat(actualTasksNames, containsInAnyOrder("Test GetListService 1",
                "Test GetListService 2"));
    }

    @Test
    public void getList_Return_Empty_Collection() {
        jsonHandler.getMap().clear();

        Collection<Task> collection = getListService.getList();

        assertTrue(collection.isEmpty());
    }
}
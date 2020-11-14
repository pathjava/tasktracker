package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * тестирование сервиса обновления задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskRefreshServiceTest {

    @Autowired
    private RefreshService<Task> refreshService;

    @Autowired
    private RemoveService<Task> removeService;

    @Autowired
    private CreateService<Task> createService;

    @Autowired
    private GetService<Long, Task> getService;

    @Autowired
    private GetListService<Task> getListService;

    @BeforeEach
    void before() {
        createService.create(
                new Task(null, "TT1-1", "Test RefreshService", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
    }

    @Test
    public void refresh() {
        Long id = getListService.getList().stream()
                .filter(e -> e.getName().equals("Test RefreshService")).findFirst()
                .map(Task::getId)
                .orElse(null);

        if (id != null) {
            refreshService.refresh(
                    new Task(id, "TT1-1", "Test RefreshService Updated", "Description task 1",
                            TaskType.BUG, null, 11L, new User(), new User(),
                            ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                            null,
                            Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                            new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
            );
            Task task = getService.get(id);

            assertThat(task.getName(), equalTo("Test RefreshService Updated"));

            removeService.remove(task);
        } else
            fail();
    }
}
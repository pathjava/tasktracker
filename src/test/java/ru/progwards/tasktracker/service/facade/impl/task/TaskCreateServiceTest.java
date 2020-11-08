package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * тестирование сервиса создания задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskCreateServiceTest {

    @Autowired
    private CreateService<Task> createService;

    @Autowired
    private RemoveService<Task> removeService;

    @Autowired
    private GetService<Long, Task> getService;

    @Autowired
    private GetListService<Task> getListService;

    @Test
    public void testCreate() {
        createService.create(
                new Task(null, "TT1-1", "Test CreateService", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );

        Long id = getListService.getList().stream()
                .filter(e -> e.getName().equals("Test CreateService")).findFirst()
                .map(Task::getId)
                .orElse(null);

        Task task = getService.get(id);

        assertThat(task.getName(), equalTo("Test CreateService"));

        removeService.remove(task);
    }
}
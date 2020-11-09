package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование сервиса получения задачи по идентификатору
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskGetServiceTest {

    @Autowired
    private GetService<Long, Task> getService;

    @Autowired
    private RemoveService<Task> removeService;

    @Autowired
    private CreateService<Task> createService;

    @Autowired
    private GetListService<Task> getListService;

    @BeforeEach
    void before() {
        createService.create(
                new Task(null, "TT1-1", "Test task 1 GetService", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
    }

    @Test
    public void testGet() {
        Long id = getListService.getList().stream()
                .filter(e -> e.getName().equals("Test task 1 GetService")).findFirst()
                .map(Task::getId)
                .orElse(null);

        if (id != null) {
            Task task = getService.get(id);

            assertThat(task, is(notNullValue()));

            assertThat(task.getName(), equalTo("Test task 1 GetService"));

            removeService.remove(task);
        } else
            fail();
    }

    @Test
    void testGet_Return_Null(){
        Task task = getService.get(anyLong());

        assertNull(task);
    }
}
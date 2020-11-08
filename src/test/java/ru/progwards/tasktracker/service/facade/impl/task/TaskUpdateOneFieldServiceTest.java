package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * тестирование сервиса обновления поля задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskUpdateOneFieldServiceTest {

    @Qualifier("taskUpdateOneFieldService")
    @Autowired
    private UpdateOneFieldService<Task> oneFieldService;

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
                new Task(null, "TT1-1", "Test task 1 UpdateOneFieldService", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
    }

    @Test
    public void testSetOneField() {
        Long id = getListService.getList().stream()
                .filter(e -> e.getName().equals("Test task 1 UpdateOneFieldService")).findFirst()
                .map(Task::getId)
                .orElse(null);

        if (id != null) {
            oneFieldService.updateOneField(
                    new UpdateOneValue(id, "Test task 1 UpdateOneFieldService updated", "name")
            );
        }

        Task task = getService.get(id);

        assertThat(task.getName(), equalTo("Test task 1 UpdateOneFieldService updated"));

        removeService.remove(task);
    }
}
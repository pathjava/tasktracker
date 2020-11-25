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
import ru.progwards.tasktracker.service.vo.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * тестирование сервиса удаления задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskRemoveServiceTest {

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
//        createService.create(
//                new Task(null, "TT1-1", "Test task", "Description RemoveService",
//                        null, null, 11L, new User(), new User(),
//                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
//                        null,
//                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
//                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
//        );
    }

    @Test
    public void remove() {
//        Long id = getListService.getList().stream()
//                .filter(e -> e.getDescription().equals("Description RemoveService")).findFirst()
//                .map(Task::getId)
//                .orElse(null);
//
//        if (id != null) {
//            removeService.remove(
//                    new Task(id, "TT1-1", "Test task", "Description RemoveService",
//                            null, null, 11L, new User(), new User(),
//                            ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
//                            null,
//                            Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
//                            new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
//            );
//            Task task = getService.get(id);
//
//            assertNull(task);
//        } else
//            fail();
    }
}
package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.Task;

import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса создания задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskCreateServiceTest {

    private final Task task;
    @Mock
    private CreateService<Task> createService;

    {
        task = new Task(
                1L, null, null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null
        );
    }

    @Test
    public void create() {
        doNothing().when(createService).create(isA(Task.class));

        createService.create(task);

        verify(createService, times(1)).create(task);
    }
}
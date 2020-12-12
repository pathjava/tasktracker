package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.Task;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса удаления задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskRemoveServiceTest {

    private final Task task;
    @Mock
    private RemoveService<Task> removeService;

    {
        task = new Task(
                1L, "TT-1", null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null
        );
    }

    @Test
    public void remove() {
        doNothing().when(removeService).remove(isA(Task.class));

        removeService.remove(task);

        verify(removeService, times(1)).remove(task);
    }
}
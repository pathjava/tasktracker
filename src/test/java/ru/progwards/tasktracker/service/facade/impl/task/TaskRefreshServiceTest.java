package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.Task;

import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса обновления задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskRefreshServiceTest {

    private final Task task;
    @Mock
    private RefreshService<Task> refreshService;

    {
        task = new Task(
                1L, "TT-1", null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null
        );
    }

    @Test
    public void refresh() {
        doNothing().when(refreshService).refresh(isA(Task.class));

        refreshService.refresh(task);

        verify(refreshService, times(1)).refresh(task);
    }
}
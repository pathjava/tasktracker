package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.RefreshService;
import ru.progwards.tasktracker.service.vo.Task;

import static org.mockito.Mockito.*;

/**
 * тестирование сервиса обновления задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TaskRefreshServiceTest {

    private final Task task;
    @Mock
    private RefreshService<Task> refreshService;

    {
        task = new Task(
                1L, "TT-1", null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null
        );
    }

    @Test
    public void refresh() {
        doNothing().when(refreshService).refresh(isA(Task.class));

        refreshService.refresh(task);

        verify(refreshService, times(1)).refresh(task);
    }
}
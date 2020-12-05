package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса получения задачи по идентификатору
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskGetServiceTest {

    private final Task task;
    @Mock
    private GetService<Long, Task> getService;

    {
        task = new Task(
                1L, "TT-1", null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null
        );
    }

    @Test
    public void get() {
        when(getService.get(anyLong())).thenReturn(task);

        Task t = getService.get(1L);

        assertThat(t.getCode(), equalTo("TT-1"));

        verify(getService, times(1)).get(1L);
    }

    @Test
    void get_Return_Null() {
        when(getService.get(anyLong())).thenReturn(null);

        Task t = getService.get(1L);

        assertNull(t);

        verify(getService, times(1)).get(1L);
    }
}
package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * @author Oleg Kiselev
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskByCodeGetServiceTest {

    private final Task task;
    @Mock
    private GetService<String, Task> getService;

    {
        task = new Task(
                1L, "TT-1", null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null
        );
    }

    @Test
    void get() {
        when(getService.get(anyString())).thenReturn(task);

        Task t = getService.get("TT-1");

        assertThat(t.getCode(), equalTo("TT-1"));

        verify(getService, times(1)).get("TT-1");
    }

    @Test
    void get_Return_Null(){
        when(getService.get(anyString())).thenReturn(null);

        Task t = getService.get("TT-1");

        assertNull(t);

        verify(getService, times(1)).get("TT-1");
    }
}
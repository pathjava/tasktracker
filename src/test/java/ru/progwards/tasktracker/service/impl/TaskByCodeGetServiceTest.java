package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.Task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static ru.progwards.tasktracker.objects.GetModel.getTask;

/**
 * Тестирование сервиса получения задачи по строковому коду
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskByCodeGetServiceTest {

    private final Task task;
    @Mock
    private GetService<String, Task> getService;

    {
        task = getTask();
        task.setId(1L);
        task.setCode("TT-1");
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
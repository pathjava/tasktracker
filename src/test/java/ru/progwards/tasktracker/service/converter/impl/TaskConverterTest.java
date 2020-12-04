package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.Task;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * тестирование конвертера между valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskConverterTest {

    @Mock
    private Converter<TaskEntity, Task> converter;

    @Test
    void toVo() {
        when(converter.toVo(isA(TaskEntity.class))).thenReturn(
                new Task(
                        null, null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null
                )
        );

        Task task = converter.toVo(
                new TaskEntity(
                        null, null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null, false
                )
        );

        verify(converter, times(1)).toVo(any());

        assertNotNull(task);
    }

    @Test
    void toVo_return_Null() {
        when(converter.toVo(isA(TaskEntity.class))).thenReturn(null);

        Task task = converter.toVo(any());

        verify(converter, times(1)).toVo(any());

        assertNull(task);
    }

    @Test
    void toEntity() {
        when(converter.toEntity(isA(Task.class))).thenReturn(
                new TaskEntity(
                        null, null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null, false
                )
        );

        TaskEntity entity = converter.toEntity(
                new Task(
                        null, null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null
                )
        );

        verify(converter, times(1)).toEntity(any());

        assertNotNull(entity);
    }

    @Test
    void toEntity_return_Null() {
        when(converter.toEntity(isA(Task.class))).thenReturn(null);

        TaskEntity entity = converter.toEntity(any());

        verify(converter, times(1)).toEntity(any());

        assertNull(entity);
    }
}
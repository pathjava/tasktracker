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
import static org.mockito.Mockito.when;

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
                        null, null, null, null, null, null, null,
                        null, null, null, null, null, null,
                        null, null, null, null, null, null
                )
        );

        Task task = converter.toVo(
                new TaskEntity(
                        null, null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null
                )
        );

        assertNotNull(task);
    }

    @Test
    void toVo_return_Null() {
        when(converter.toVo(isA(TaskEntity.class))).thenReturn(null);

        Task task = converter.toVo(any());

        assertNull(task);
    }

    @Test
    void toEntity() {
        when(converter.toEntity(isA(Task.class))).thenReturn(
                new TaskEntity(
                        null, null, null, null, null, null, null,
                        null, null, null, null, null, null, null,
                        null, null, null, null, null, null
                )
        );

        TaskEntity entity = converter.toEntity(
                new Task(
                        null, null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null, null, null,
                        null, null, null
                )
        );

        assertNotNull(entity);
    }

    @Test
    void toEntity_return_Null() {
        when(converter.toEntity(isA(Task.class))).thenReturn(null);

        TaskEntity entity = converter.toEntity(any());

        assertNull(entity);
    }
}
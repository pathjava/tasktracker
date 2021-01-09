package ru.progwards.tasktracker.dto.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskDtoFull;
import ru.progwards.tasktracker.model.Task;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * Тестирование конвертера между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskDtoFullConverterTest {

    @Mock
    private Converter<Task, TaskDtoFull> converter;

    private final Task model = new Task(
            null, null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null,
            null, null, null, null, null, false
    );

    private final TaskDtoFull dtoPreview = new TaskDtoFull(
            null, null, null, null, null, null,
            null, null, null, null, null, null,
            null, null, null, null, null, null
    );

    @Test
    void toModel() {
        when(converter.toModel(isA(TaskDtoFull.class))).thenReturn(model);

        Task model = converter.toModel(dtoPreview);

        assertNotNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toModel_Return_Null() {
        when(converter.toModel(isA(TaskDtoFull.class))).thenReturn(null);

        Task model = converter.toModel(any());

        assertNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toDto() {
        when(converter.toDto(isA(Task.class))).thenReturn(dtoPreview);

        TaskDtoFull dto = converter.toDto(model);

        assertNotNull(dto);

        verify(converter, times(1)).toDto(any());
    }

    @Test
    void toDto_Return_Null() {
        when(converter.toDto(isA(Task.class))).thenReturn(null);

        TaskDtoFull dto = converter.toDto(any());

        assertNull(dto);

        verify(converter, times(1)).toDto(any());
    }
}
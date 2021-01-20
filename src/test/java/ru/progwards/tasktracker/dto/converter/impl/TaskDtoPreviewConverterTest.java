package ru.progwards.tasktracker.dto.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.Task;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ru.progwards.tasktracker.objects.GetDtoPreview.getTaskDtoPreview;
import static ru.progwards.tasktracker.objects.GetModel.getTask;

/**
 * Тестирование конвертера между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskDtoPreviewConverterTest {

    @Mock
    private Converter<Task, TaskDtoPreview> converter;

    private final Task model = getTask();

    private final TaskDtoPreview dtoPreview = getTaskDtoPreview();

    @Test
    void toModel() {
        when(converter.toModel(isA(TaskDtoPreview.class))).thenReturn(model);

        Task model = converter.toModel(dtoPreview);

        assertNotNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toModel_Return_Null() {
        when(converter.toModel(isA(TaskDtoPreview.class))).thenReturn(null);

        Task model = converter.toModel(any());

        assertNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toDto() {
        when(converter.toDto(isA(Task.class))).thenReturn(dtoPreview);

        TaskDtoPreview dto = converter.toDto(model);

        assertNotNull(dto);

        verify(converter, times(1)).toDto(any());
    }

    @Test
    void toDto_Return_Null() {
        when(converter.toDto(isA(Task.class))).thenReturn(null);

        TaskDtoPreview dto = converter.toDto(any());

        assertNull(dto);

        verify(converter, times(1)).toDto(any());
    }
}
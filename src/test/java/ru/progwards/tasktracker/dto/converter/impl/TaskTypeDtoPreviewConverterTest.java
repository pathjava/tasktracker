package ru.progwards.tasktracker.dto.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.dto.TaskTypeDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.TaskType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ru.progwards.tasktracker.objects.GetDtoPreview.getTaskTypeDtoPreview;
import static ru.progwards.tasktracker.objects.GetModel.getTaskTypeModel;

/**
 * Тестирование конвертера между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskTypeDtoPreviewConverterTest {

    @Mock
    private Converter<TaskType, TaskTypeDtoPreview> converter;

    private final TaskType model = getTaskTypeModel();

    private final TaskTypeDtoPreview dtoPreview = getTaskTypeDtoPreview();

    @Test
    void toModel() {
        when(converter.toModel(isA(TaskTypeDtoPreview.class))).thenReturn(model);

        TaskType model = converter.toModel(dtoPreview);

        assertNotNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toModel_Return_Null() {
        when(converter.toModel(isA(TaskTypeDtoPreview.class))).thenReturn(null);

        TaskType model = converter.toModel(any());

        assertNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toDto() {
        when(converter.toDto(isA(TaskType.class))).thenReturn(dtoPreview);

        TaskTypeDtoPreview dto = converter.toDto(model);

        assertNotNull(dto);

        verify(converter, times(1)).toDto(any());
    }

    @Test
    void toDto_Return_Null() {
        when(converter.toDto(isA(TaskType.class))).thenReturn(null);

        TaskTypeDtoPreview dto = converter.toDto(any());

        assertNull(dto);

        verify(converter, times(1)).toDto(any());
    }
}
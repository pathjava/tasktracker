package ru.progwards.tasktracker.controller.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.service.vo.TaskType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * Тестирование конвертера между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskTypeDtoFullConverterTest {

    @Mock
    private Converter<TaskType, TaskTypeDtoFull> converter;

    private final TaskType valueObject = new TaskType(
            null, null, null, null
    );

    private final TaskTypeDtoFull dtoPreview = new TaskTypeDtoFull(
            null, null, null
    );

    @Test
    void toModel() {
        when(converter.toModel(isA(TaskTypeDtoFull.class))).thenReturn(valueObject);

        TaskType model = converter.toModel(dtoPreview);

        assertNotNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toModel_Return_Null() {
        when(converter.toModel(isA(TaskTypeDtoFull.class))).thenReturn(null);

        TaskType model = converter.toModel(any());

        assertNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toDto() {
        when(converter.toDto(isA(TaskType.class))).thenReturn(dtoPreview);

        TaskTypeDtoFull dto = converter.toDto(valueObject);

        assertNotNull(dto);

        verify(converter, times(1)).toDto(any());
    }

    @Test
    void toDto_Return_Null() {
        when(converter.toDto(isA(TaskType.class))).thenReturn(null);

        TaskTypeDtoFull dto = converter.toDto(any());

        assertNull(dto);

        verify(converter, times(1)).toDto(any());
    }
}
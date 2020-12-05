package ru.progwards.tasktracker.controller.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelatedTaskDtoFull;
import ru.progwards.tasktracker.service.vo.RelatedTask;

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
class RelatedTaskDtoFullConverterTest {

    @Mock
    private Converter<RelatedTask, RelatedTaskDtoFull> converter;

    private final RelatedTask valueObject = new RelatedTask(
            null, null, null, null
    );

    private final RelatedTaskDtoFull dtoPreview = new RelatedTaskDtoFull(
            null, null, null, null
    );

    @Test
    void toModel() {
        when(converter.toModel(isA(RelatedTaskDtoFull.class))).thenReturn(valueObject);

        RelatedTask model = converter.toModel(dtoPreview);

        assertNotNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toModel_Return_Null() {
        when(converter.toModel(isA(RelatedTaskDtoFull.class))).thenReturn(null);

        RelatedTask model = converter.toModel(any());

        assertNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toDto() {
        when(converter.toDto(isA(RelatedTask.class))).thenReturn(dtoPreview);

        RelatedTaskDtoFull dto = converter.toDto(valueObject);

        assertNotNull(dto);

        verify(converter, times(1)).toDto(any());
    }

    @Test
    void toDto_Return_Null() {
        when(converter.toDto(isA(RelatedTask.class))).thenReturn(null);

        RelatedTaskDtoFull dto = converter.toDto(any());

        assertNull(dto);

        verify(converter, times(1)).toDto(any());
    }
}
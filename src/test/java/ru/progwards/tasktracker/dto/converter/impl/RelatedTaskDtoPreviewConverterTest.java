package ru.progwards.tasktracker.dto.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.RelatedTaskDtoPreview;
import ru.progwards.tasktracker.model.RelatedTask;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ru.progwards.tasktracker.objects.GetDtoPreview.getRelatedTaskDtoPreview;
import static ru.progwards.tasktracker.objects.GetModel.getRelatedTaskModel;

/**
 * Тестирование конвертера между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskDtoPreviewConverterTest {

    @Mock
    private Converter<RelatedTask, RelatedTaskDtoPreview> converter;

    private final RelatedTask model = getRelatedTaskModel();

    private final RelatedTaskDtoPreview dtoPreview = getRelatedTaskDtoPreview();

    @Test
    void toModel() {
        when(converter.toModel(isA(RelatedTaskDtoPreview.class))).thenReturn(model);

        RelatedTask model = converter.toModel(dtoPreview);

        assertNotNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toModel_Return_Null() {
        when(converter.toModel(isA(RelatedTaskDtoPreview.class))).thenReturn(null);

        RelatedTask model = converter.toModel(any());

        assertNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toDto() {
        when(converter.toDto(isA(RelatedTask.class))).thenReturn(dtoPreview);

        RelatedTaskDtoPreview dto = converter.toDto(model);

        assertNotNull(dto);

        verify(converter, times(1)).toDto(any());
    }

    @Test
    void toDto_Return_Null() {
        when(converter.toDto(isA(RelatedTask.class))).thenReturn(null);

        RelatedTaskDtoPreview dto = converter.toDto(any());

        assertNull(dto);

        verify(converter, times(1)).toDto(any());
    }
}
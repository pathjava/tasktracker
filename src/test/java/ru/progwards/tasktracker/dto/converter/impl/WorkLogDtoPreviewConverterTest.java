package ru.progwards.tasktracker.dto.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.WorkLogDtoPreview;
import ru.progwards.tasktracker.model.WorkLog;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ru.progwards.tasktracker.objects.GetDtoPreview.getWorkLogDtoPreview;
import static ru.progwards.tasktracker.objects.GetModel.getWorkLogModel;

/**
 * Тестирование конвертера между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogDtoPreviewConverterTest {

    @Mock
    private Converter<WorkLog, WorkLogDtoPreview> converter;

    private final WorkLog model = getWorkLogModel();

    private final WorkLogDtoPreview dtoPreview = getWorkLogDtoPreview();

    @Test
    void toModel() {
        when(converter.toModel(isA(WorkLogDtoPreview.class))).thenReturn(model);

        WorkLog model = converter.toModel(dtoPreview);

        assertNotNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toModel_Return_Null() {
        when(converter.toModel(isA(WorkLogDtoPreview.class))).thenReturn(null);

        WorkLog model = converter.toModel(any());

        assertNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toDto() {
        when(converter.toDto(isA(WorkLog.class))).thenReturn(dtoPreview);

        WorkLogDtoPreview dto = converter.toDto(model);

        assertNotNull(dto);

        verify(converter, times(1)).toDto(any());
    }

    @Test
    void toDto_Return_Null() {
        when(converter.toDto(isA(WorkLog.class))).thenReturn(null);

        WorkLogDtoPreview dto = converter.toDto(any());

        assertNull(dto);

        verify(converter, times(1)).toDto(any());
    }
}
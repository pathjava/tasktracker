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

/**
 * Тестирование конвертера между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogDtoPreviewConverterTest {

    @Mock
    private Converter<WorkLog, WorkLogDtoPreview> converter;

    private final WorkLog model = new WorkLog(
            null, null, null, null, null,
            null, null, null
    );

    private final WorkLogDtoPreview dtoPreview = new WorkLogDtoPreview(
            null, null, null, null
    );

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
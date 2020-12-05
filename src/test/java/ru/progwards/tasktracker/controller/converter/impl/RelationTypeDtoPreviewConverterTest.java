package ru.progwards.tasktracker.controller.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelationTypeDtoPreview;
import ru.progwards.tasktracker.service.vo.RelationType;

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
class RelationTypeDtoPreviewConverterTest {

    @Mock
    private Converter<RelationType, RelationTypeDtoPreview> converter;

    private final RelationType valueObject = new RelationType(
            null, null, null
    );

    private final RelationTypeDtoPreview dtoPreview = new RelationTypeDtoPreview(
            null, null
    );

    @Test
    void toModel() {
        when(converter.toModel(isA(RelationTypeDtoPreview.class))).thenReturn(valueObject);

        RelationType model = converter.toModel(dtoPreview);

        assertNotNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toModel_Return_Null() {
        when(converter.toModel(isA(RelationTypeDtoPreview.class))).thenReturn(null);

        RelationType model = converter.toModel(dtoPreview);

        assertNull(model);

        verify(converter, times(1)).toModel(any());
    }

    @Test
    void toDto() {
        when(converter.toDto(isA(RelationType.class))).thenReturn(dtoPreview);

        RelationTypeDtoPreview dto = converter.toDto(valueObject);

        assertNotNull(dto);

        verify(converter, times(1)).toDto(any());
    }

    @Test
    void toDto_Return_Null() {
        when(converter.toDto(isA(RelationType.class))).thenReturn(null);

        RelationTypeDtoPreview dto = converter.toDto(any());

        assertNull(dto);

        verify(converter, times(1)).toDto(any());
    }
}
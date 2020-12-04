package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * тестирование конвертера между valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelationTypeConverterTest {

    @Mock
    private Converter<RelationTypeEntity, RelationType> converter;

    @Test
    void toVo() {
        when(converter.toVo(isA(RelationTypeEntity.class))).thenReturn(
                new RelationType(null, null, null)
        );

        RelationType type = converter.toVo(
                new RelationTypeEntity(null, null, null)
        );

        verify(converter, times(1)).toVo(any());

        assertNotNull(type);
    }

    @Test
    void toVo_Return_Null() {
        when(converter.toVo(isA(RelationTypeEntity.class))).thenReturn(null);

        RelationType type = converter.toVo(any());

        verify(converter, times(1)).toVo(any());

        assertNull(type);
    }

    @Test
    void toEntity() {
        when(converter.toEntity(isA(RelationType.class))).thenReturn(
                new RelationTypeEntity(null, null, null)
        );

        RelationTypeEntity entity = converter.toEntity(
                new RelationType(null, null, null)
        );

        verify(converter, times(1)).toEntity(any());

        assertNotNull(entity);
    }

    @Test
    void toEntity_Return_Null() {
        when(converter.toEntity(isA(RelationType.class))).thenReturn(null);

        RelationTypeEntity entity = converter.toEntity(any());

        verify(converter, times(1)).toEntity(any());

        assertNull(entity);
    }
}
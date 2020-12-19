package ru.progwards.tasktracker.repository.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.repository.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * Тестирование конвертера между valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelationTypeConverterTest {

    @Mock
    private Converter<RelationTypeEntity, RelationType> converter;

    private final RelationType valueObject = new RelationType(
            null, null, null, null
    );

    private final RelationTypeEntity entity = new RelationTypeEntity(
            null, null, null
    );

    @Test
    void toVo() {
        when(converter.toVo(isA(RelationTypeEntity.class))).thenReturn(valueObject);

        RelationType vo = converter.toVo(entity);

        verify(converter, times(1)).toVo(any());

        assertNotNull(vo);
    }

    @Test
    void toVo_Return_Null() {
        when(converter.toVo(isA(RelationTypeEntity.class))).thenReturn(null);

        RelationType vo = converter.toVo(any());

        verify(converter, times(1)).toVo(any());

        assertNull(vo);
    }

    @Test
    void toEntity() {
        when(converter.toEntity(isA(RelationType.class))).thenReturn(entity);

        RelationTypeEntity entity = converter.toEntity(valueObject);

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
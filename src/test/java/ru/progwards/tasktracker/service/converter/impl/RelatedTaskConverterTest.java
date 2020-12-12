package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.RelatedTask;
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
class RelatedTaskConverterTest {

    @Mock
    private Converter<RelatedTaskEntity, RelatedTask> converter;

    private final RelatedTask valueObject = new RelatedTask(
            null, new RelationType(null, null, null),
            null, null
    );

    private final RelatedTaskEntity entity = new RelatedTaskEntity(
            null, new RelationTypeEntity(null, null, null),
            null, null, false
    );

    @Test
    void toVo() {
        when(converter.toVo(isA(RelatedTaskEntity.class))).thenReturn(valueObject);

        RelatedTask vo = converter.toVo(entity);

        verify(converter, times(1)).toVo(any());

        assertNotNull(vo);
    }

    @Test
    void toVo_Return_Null() {
        when(converter.toVo(isA(RelatedTaskEntity.class))).thenReturn(null);

        RelatedTask vo = converter.toVo(any());

        verify(converter, times(1)).toVo(any());

        assertNull(vo);
    }

    @Test
    void toEntity() {
        when(converter.toEntity(isA(RelatedTask.class))).thenReturn(entity);

        RelatedTaskEntity entity = converter.toEntity(valueObject);

        verify(converter, times(1)).toEntity(any());

        assertNotNull(entity);
    }

    @Test
    void toEntity_Return_Null() {
        when(converter.toEntity(isA(RelatedTask.class))).thenReturn(null);

        RelatedTaskEntity entity = converter.toEntity(any());

        verify(converter, times(1)).toEntity(any());

        assertNull(entity);
    }
}
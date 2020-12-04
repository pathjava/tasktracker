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
 * тестирование конвертера между valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskConverterTest {

    @Mock
    private Converter<RelatedTaskEntity, RelatedTask> converter;

    @Test
    void toVo() {
        when(converter.toVo(isA(RelatedTaskEntity.class))).thenReturn(
                new RelatedTask(
                        null, new RelationType(null, null, null),
                        null, null
                )
        );

        RelatedTask task = converter.toVo(
                new RelatedTaskEntity(
                        null, new RelationTypeEntity(null, null, null),
                        null, null, false
                )
        );

        verify(converter, times(1)).toVo(any());

        assertNotNull(task);
    }

    @Test
    void toVo_Return_Null() {
        when(converter.toVo(isA(RelatedTaskEntity.class))).thenReturn(null);

        RelatedTask task = converter.toVo(any());

        verify(converter, times(1)).toVo(any());

        assertNull(task);
    }

    @Test
    void toEntity() {
        when(converter.toEntity(isA(RelatedTask.class))).thenReturn(
                new RelatedTaskEntity(
                        null, new RelationTypeEntity(null, null, null),
                        null, null, false
                )
        );

        RelatedTaskEntity entity = converter.toEntity(
                new RelatedTask(
                        null, new RelationType(null, null, null),
                        null, null
                )
        );

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
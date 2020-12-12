package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.TaskTypeEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.TaskType;

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
class TaskTypeConverterTest {

    @Mock
    private Converter<TaskTypeEntity, TaskType> converter;

    private final TaskType valueObject = new TaskType(
            null, null, null, null
    );

    private final TaskTypeEntity entity = new TaskTypeEntity(
            null, null, null, null
    );

    @Test
    void toVo() {
        when(converter.toVo(isA(TaskTypeEntity.class))).thenReturn(valueObject);

        TaskType vo = converter.toVo(entity);

        verify(converter, times(1)).toVo(any());

        assertNotNull(vo);
    }

    @Test
    void toVo_Return_Null() {
        when(converter.toVo(isA(TaskTypeEntity.class))).thenReturn(null);

        TaskType vo = converter.toVo(any());

        verify(converter, times(1)).toVo(any());

        assertNull(vo);
    }

    @Test
    void toEntity() {
        when(converter.toEntity(isA(TaskType.class))).thenReturn(entity);

        TaskTypeEntity entity = converter.toEntity(valueObject);

        verify(converter, times(1)).toEntity(any());

        assertNotNull(entity);
    }

    @Test
    void toEntity_Return_Null() {
        when(converter.toEntity(isA(TaskType.class))).thenReturn(null);

        TaskTypeEntity entity = converter.toEntity(any());

        verify(converter, times(1)).toEntity(any());

        assertNull(entity);
    }
}
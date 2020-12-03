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
import static org.mockito.Mockito.when;

/**
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskTypeConverterTest {

    @Mock
    private Converter<TaskTypeEntity, TaskType> converter;

    @Test
    void toVo() {
        when(converter.toVo(isA(TaskTypeEntity.class))).thenReturn(
                new TaskType(null, null, null, null)
        );

        TaskType taskType = converter.toVo(
                new TaskTypeEntity(null, null, null, null)
        );

        assertNotNull(taskType);
    }

    @Test
    void toVo_Return_Null() {
        when(converter.toVo(isA(TaskTypeEntity.class))).thenReturn(null);

        TaskType taskType = converter.toVo(any());

        assertNull(taskType);
    }

    @Test
    void toEntity() {
        when(converter.toEntity(isA(TaskType.class))).thenReturn(
                new TaskTypeEntity(null, null, null, null)
        );

        TaskTypeEntity entity = converter.toEntity(
                new TaskType(null, null, null, null)
        );

        assertNotNull(entity);
    }

    @Test
    void toEntity_Return_Null() {
        when(converter.toEntity(isA(TaskType.class))).thenReturn(null);

        TaskTypeEntity entity = converter.toEntity(any());

        assertNull(entity);
    }
}
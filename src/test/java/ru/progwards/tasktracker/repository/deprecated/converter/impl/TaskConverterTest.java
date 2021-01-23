//package ru.progwards.tasktracker.repository.deprecated.converter.impl;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
//import ru.progwards.tasktracker.repository.deprecated.converter.Converter;
//import ru.progwards.tasktracker.model.Task;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.isA;
//import static org.mockito.Mockito.*;
//
///**
// * Тестирование конвертера между valueObject <-> entity
// *
// * @author Oleg Kiselev
// */
//@SpringBootTest
//@Deprecated
//class TaskConverterTest {
//
//    @Mock
//    private Converter<TaskEntity, Task> converter;
//
//    private final Task valueObject = new Task(
//            null, null, null, null, null, null,
//            null, null, null, null,
//            null, null, null, null, null,
//            null, null, null, null, null, false
//    );
//
//    private final TaskEntity entity = new TaskEntity(
//            null, null, null, null, null, null,
//            null, null, null, null, null,
//            null, null, null, null, null,
//            null, null, null, null, false
//    );
//
//    @Test
//    void toVo() {
//        when(converter.toVo(isA(TaskEntity.class))).thenReturn(valueObject);
//
//        Task vo = converter.toVo(entity);
//
//        verify(converter, times(1)).toVo(any());
//
//        assertNotNull(vo);
//    }
//
//    @Test
//    void toVo_return_Null() {
//        when(converter.toVo(isA(TaskEntity.class))).thenReturn(null);
//
//        Task vo = converter.toVo(any());
//
//        verify(converter, times(1)).toVo(any());
//
//        assertNull(vo);
//    }
//
//    @Test
//    void toEntity() {
//        when(converter.toEntity(isA(Task.class))).thenReturn(entity);
//
//        TaskEntity entity = converter.toEntity(valueObject);
//
//        verify(converter, times(1)).toEntity(any());
//
//        assertNotNull(entity);
//    }
//
//    @Test
//    void toEntity_return_Null() {
//        when(converter.toEntity(isA(Task.class))).thenReturn(null);
//
//        TaskEntity entity = converter.toEntity(any());
//
//        verify(converter, times(1)).toEntity(any());
//
//        assertNull(entity);
//    }
//}
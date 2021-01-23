//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.RepositoryByCode;
//import ru.progwards.tasktracker.repository.deprecated.RepositoryByProjectId;
//import ru.progwards.tasktracker.repository.deprecated.RepositoryUpdateField;
//import ru.progwards.tasktracker.repository.deprecated.entity.TaskEntity;
//import ru.progwards.tasktracker.model.UpdateOneValue;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.containsInAnyOrder;
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
///**
// * Тестирование методов работы с репозиторием задач
// *
// * @author Oleg Kiselev
// */
//@SpringBootTest
//@Deprecated
//public class TaskRepositoryTest {
//
//    private final List<TaskEntity> entities = new ArrayList<>();
//    @Mock
//    private Repository<Long, TaskEntity> repository;
//    @Mock
//    private RepositoryByProjectId<Long, TaskEntity> byProjectId;
//    @Mock
//    private RepositoryUpdateField<TaskEntity> updateField;
//    @Mock
//    private RepositoryByCode<String, TaskEntity> byCode;
//
//    {
//        for (int i = 0; i < 3; i++) {
//            entities.add(
//                    new TaskEntity(
//                            1L + i, "TT-" + (1 + i), null, null, null,
//                            null, null, null, null, null,
//                            null, null, null, null, null,
//                            null, null, null, null, null, false
//                    )
//            );
//        }
//    }
//
//    @Test
//    void get() {
//        when(repository.get(anyLong())).thenReturn(entities.get(0));
//
//        TaskEntity entity = repository.get(1L);
//
//        assertNotNull(entity);
//
//        assertThat(entity.getCode(), equalTo("TT-1"));
//
//        verify(repository, times(1)).get(1L);
//    }
//
//    @Test
//    void get_Return_Null() {
//        when(repository.get(anyLong())).thenReturn(null);
//
//        TaskEntity entity = repository.get(1L);
//
//        assertNull(entity);
//
//        verify(repository, times(1)).get(1L);
//    }
//
//    @Test
//    void getList() {
//        when(repository.get()).thenReturn(entities);
//
//        Collection<TaskEntity> collection = repository.get();
//
//        assertThat(collection.size(), equalTo(3));
//
//        verify(repository, times(1)).get();
//
//        assertThat(
//                collection.stream()
//                        .map(TaskEntity::getCode)
//                        .collect(Collectors.toList()),
//                containsInAnyOrder("TT-1", "TT-2", "TT-3")
//        );
//    }
//
//    @Test
//    void getList_Return_Empty_Collection() {
//        when(repository.get()).thenReturn(Collections.emptyList());
//
//        Collection<TaskEntity> collection = repository.get();
//
//        assertTrue(collection.isEmpty());
//
//        verify(repository, times(1)).get();
//    }
//
//    @Test
//    void create() {
//        doNothing().when(repository).create(isA(TaskEntity.class));
//
//        repository.create(entities.get(0));
//
//        verify(repository, times(1)).create(entities.get(0));
//    }
//
//    @Test
//    void update() {
//        doNothing().when(repository).update(isA(TaskEntity.class));
//
//        repository.update(entities.get(0));
//
//        verify(repository, times(1)).update(entities.get(0));
//    }
//
//    @Test
//    void delete() {
//        doNothing().when(repository).delete(anyLong());
//
//        repository.delete(1L);
//
//        verify(repository, times(1)).delete(1L);
//    }
//
//    @Test
//    void getByProjectId() {
//        when(byProjectId.getByProjectId(anyLong())).thenReturn(entities);
//
//        Collection<TaskEntity> collection = byProjectId.getByProjectId(1L);
//
//        assertThat(collection.size(), equalTo(3));
//
//        verify(byProjectId, times(1)).getByProjectId(1L);
//
//        assertThat(
//                collection.stream()
//                        .map(TaskEntity::getCode)
//                        .collect(Collectors.toList()),
//                containsInAnyOrder("TT-1", "TT-2", "TT-3")
//        );
//    }
//
//    @Test
//    void getByProjectId_Return_Empty_Collection() {
//        when(byProjectId.getByProjectId(anyLong())).thenReturn(Collections.emptyList());
//
//        Collection<TaskEntity> collection = byProjectId.getByProjectId(1L);
//
//        assertTrue(collection.isEmpty());
//
//        verify(byProjectId, times(1)).getByProjectId(1L);
//    }
//
//    @Test
//    void updateField() {
//        doNothing().when(updateField).updateField(
//                new UpdateOneValue(
//                        1L, "Test task 1 UpdateOneFieldService updated", "name"
//                )
//        );
//
//        updateField.updateField(any());
//
//        verify(updateField, times(1)).updateField(any());
//    }
//
//    @Test
//    void getByCode() {
//        when(byCode.getByCode(anyString())).thenReturn(entities.get(0));
//
//        TaskEntity entity = byCode.getByCode("TT-1");
//
//        assertNotNull(entity);
//
//        assertThat(entity.getCode(), equalTo("TT-1"));
//
//        verify(byCode, times(1)).getByCode("TT-1");
//    }
//
//    @Test
//    void getByCode_Return_Null() {
//        when(byCode.getByCode(anyString())).thenReturn(null);
//
//        TaskEntity entity = byCode.getByCode("TT-1");
//
//        assertNull(entity);
//
//        verify(byCode, times(1)).getByCode("TT-1");
//    }
//}
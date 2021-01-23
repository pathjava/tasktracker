//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.RepositoryByProjectId;
//import ru.progwards.tasktracker.repository.deprecated.entity.TaskTypeEntity;
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
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
///**
// * Тестирование методов репозитория типов задач
// *
// * @author Oleg Kiselev
// */
//@SpringBootTest
//@Deprecated
//class TaskTypeRepositoryTest {
//
//    private final List<TaskTypeEntity> entities = new ArrayList<>();
//    @Mock
//    private Repository<Long, TaskTypeEntity> repository;
//    @Mock
//    private RepositoryByProjectId<Long, TaskTypeEntity> byProjectId;
//
//    {
//        for (int i = 0; i < 3; i++) {
//            entities.add(
//                    new TaskTypeEntity(
//                            1L + i, null, null, "type " + (1 + i)
//                    )
//            );
//        }
//    }
//
//    @Test
//    void get() {
//        when(repository.get(anyLong())).thenReturn(entities.get(0));
//
//        TaskTypeEntity entity = repository.get(1L);
//
//        assertNotNull(entity);
//
//        assertThat(entity.getName(), equalTo("type 1"));
//
//        verify(repository, times(1)).get(1L);
//    }
//
//    @Test
//    void get_Return_Null() {
//        when(repository.get(anyLong())).thenReturn(null);
//
//        TaskTypeEntity entity = repository.get(1L);
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
//        Collection<TaskTypeEntity> collection = repository.get();
//
//        assertThat(collection.size(), equalTo(3));
//
//        verify(repository, times(1)).get();
//
//        assertThat(
//                collection.stream()
//                        .map(TaskTypeEntity::getName)
//                        .collect(Collectors.toList()),
//                containsInAnyOrder("type 1", "type 2", "type 3")
//        );
//    }
//
//    @Test
//    void getList_Return_Empty_Collection() {
//        when(repository.get()).thenReturn(Collections.emptyList());
//
//        Collection<TaskTypeEntity> collection = repository.get();
//
//        assertTrue(collection.isEmpty());
//
//        verify(repository, times(1)).get();
//    }
//
//    @Test
//    void create() {
//        doNothing().when(repository).create(isA(TaskTypeEntity.class));
//
//        repository.create(entities.get(0));
//
//        verify(repository, times(1)).create(entities.get(0));
//    }
//
//    @Test
//    void update() {
//        doNothing().when(repository).update(isA(TaskTypeEntity.class));
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
//        Collection<TaskTypeEntity> collection = byProjectId.getByProjectId(1L);
//
//        assertThat(collection.size(), equalTo(3));
//
//        verify(byProjectId, times(1)).getByProjectId(1L);
//
//        assertThat(
//                collection.stream()
//                        .map(TaskTypeEntity::getName)
//                        .collect(Collectors.toList()),
//                containsInAnyOrder("type 1", "type 2", "type 3")
//        );
//    }
//
//    @Test
//    void getByProjectId_Return_Empty_Collection() {
//        when(byProjectId.getByProjectId(anyLong())).thenReturn(Collections.emptyList());
//
//        Collection<TaskTypeEntity> collection = byProjectId.getByProjectId(1L);
//
//        assertTrue(collection.isEmpty());
//
//        verify(byProjectId, times(1)).getByProjectId(1L);
//    }
//}
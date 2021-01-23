//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.entity.RelationTypeEntity;
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
// * Тестирование методов репозитория типов связей задач
// *
// * @author Oleg Kiselev
// */
//@SpringBootTest
//@Deprecated
//class RelationTypeRepositoryTest {
//
//    private final List<RelationTypeEntity> entities = new ArrayList<>();
//    @Mock
//    private Repository<Long, RelationTypeEntity> repository;
//
//    {
//        for (int i = 0; i < 3; i++) {
//            entities.add(
//                    new RelationTypeEntity(1L + i, "type " + (1 + i), null)
//            );
//        }
//    }
//
//    @Test
//    void get() {
//        when(repository.get(anyLong())).thenReturn(entities.get(0));
//
//        RelationTypeEntity entity = repository.get(1L);
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
//        RelationTypeEntity entity = repository.get(1L);
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
//        Collection<RelationTypeEntity> collection = repository.get();
//
//        assertThat(collection.size(), equalTo(3));
//
//        verify(repository, times(1)).get();
//
//        assertThat(
//                collection.stream()
//                        .map(RelationTypeEntity::getName)
//                        .collect(Collectors.toList()),
//                containsInAnyOrder("type 1", "type 2", "type 3")
//        );
//    }
//
//    @Test
//    void getList_Return_Empty_Collection() {
//        when(repository.get()).thenReturn(Collections.emptyList());
//
//        Collection<RelationTypeEntity> collection = repository.get();
//
//        assertTrue(collection.isEmpty());
//
//        verify(repository, times(1)).get();
//    }
//
//    @Test
//    void create() {
//        doNothing().when(repository).create(isA(RelationTypeEntity.class));
//
//        repository.create(entities.get(0));
//
//        verify(repository, times(1)).create(entities.get(0));
//    }
//
//    @Test
//    void update() {
//        doNothing().when(repository).update(isA(RelationTypeEntity.class));
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
//}
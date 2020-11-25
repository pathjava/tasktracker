package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование создания и удаления связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskEntityRepositoryTest {

    @Autowired
    private Repository<Long, RelatedTaskEntity> repository;

    @Autowired
    private RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId;

    @Autowired
    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    @Test
    void create() {
        RelatedTaskEntity entity = createEntityForTest();

        repository.create(entity);

        Long id = getIdCreatedEntity();

        if (id != null) {
            entity = repository.get(id);

            assertNotNull(entity);

            assertEquals("блокирующая Test", entity.getRelationTypeEntity().getName());

            repository.delete(id);
        } else
            fail();
    }

    @Test
    void delete() {
        RelatedTaskEntity entity = createEntityForTest();

        repository.create(entity);

        Long id = getIdCreatedEntity();

        if (id != null) {
            repository.delete(id);

            assertNull(repository.get(id));
        } else
            fail();
    }

    @Test
    void get() {
        RelatedTaskEntity entity = createEntityForTest();

        repository.create(entity);

        Long id = getIdCreatedEntity();

        if (id != null) {
            entity = repository.get(id);

            assertNotNull(entity);

            assertEquals("блокирующая Test", entity.getRelationTypeEntity().getName());

            repository.delete(id);
        } else
            fail();
    }


    @Test
    void getAll() {
        createEntitiesForGetAllTest();

        Collection<RelatedTaskEntity> collection = repository.get();

        assertEquals(2, collection.size());

        assertNotNull(collection);

        List<String> list = collection.stream()
                .map(entity -> entity.getRelationTypeEntity().getName())
                .collect(Collectors.toList());

        assertThat(list, containsInAnyOrder("блокирующая Test 1",
                "блокирующая Test 2"));

        jsonHandler.getMap().clear();
    }

    @Test
    void getByTaskId_Return_One_RelatedTask() {
        createEntitiesForGetAllTest();

        Collection<RelatedTaskEntity> collection = byTaskId.getByTaskId(2L);

        assertEquals(collection.size(), 2);

        jsonHandler.getMap().clear();
    }

    @Test
    public void getByTaskId_Return_Empty_Collection() {
        Collection<RelatedTaskEntity> collection = byTaskId.getByTaskId(anyLong());

        assertTrue(collection.isEmpty());
    }

    private void createEntitiesForGetAllTest() {
        jsonHandler.getMap().clear();

        repository.create(
                new RelatedTaskEntity(
                        null, new RelationTypeEntity(1L, "блокирующая Test 1", 2L),
                        1L, 2L
                )
        );
        repository.create(
                new RelatedTaskEntity(
                        null, new RelationTypeEntity(2L, "блокирующая Test 2", 2L),
                        1L, 2L
                )
        );
    }

    private RelatedTaskEntity createEntityForTest() {
        return new RelatedTaskEntity(
                null, new RelationTypeEntity(1L, "блокирующая Test", 2L),
                1L, 2L
        );
    }

    private Long getIdCreatedEntity() {
        return repository.get().stream()
                .filter(e -> e.getRelationTypeEntity().getName().equals("блокирующая Test")).findFirst()
                .map(RelatedTaskEntity::getId)
                .orElse(null);
    }
}
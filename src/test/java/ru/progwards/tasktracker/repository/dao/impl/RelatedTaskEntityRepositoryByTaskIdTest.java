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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование репозитория по получению связанных задач по идентификатору задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskEntityRepositoryByTaskIdTest {

    @Autowired
    private RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId;

    @Autowired
    private Repository<Long, RelatedTaskEntity> repository;

    @Autowired
    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    @Test
    void getByTaskId_Return_One_RelatedTask() {
        creatingEntitiesForGetAllTest();

        Collection<RelatedTaskEntity> collection = byTaskId.getByTaskId(2L);

        assertEquals(collection.size(), 2);

        jsonHandler.getMap().clear();
    }

    @Test
    public void getByTaskId_Return_Empty_Collection() {
        Collection<RelatedTaskEntity> collection = byTaskId.getByTaskId(anyLong());

        assertTrue(collection.isEmpty());
    }

    private void creatingEntitiesForGetAllTest() {
        jsonHandler.getMap().clear();

        repository.create(
                new RelatedTaskEntity(
                        null, new RelationTypeEntity(null, "блокирующая Test 1", new RelationTypeEntity(
                        2L, "блокируемая", null)),
                        1L, 2L
                )
        );
        repository.create(
                new RelatedTaskEntity(
                        null, new RelationTypeEntity(null, "блокирующая Test 2", new RelationTypeEntity(
                        2L, "блокируемая", null)),
                        1L, 2L
                )
        );
    }
}
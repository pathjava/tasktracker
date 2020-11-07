package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * тестирование репозитория по получению связанных задач по идентификатору задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskEntityRepositoryByTaskIdTest {

    @Autowired
    private RepositoryByTaskId<Long, RelatedTaskEntity> repository;

    @Test
    void getByTaskId_Return_One_RelatedTask() {
        Collection<RelatedTaskEntity> collection = repository.getByTaskId(1L);

        assertEquals(collection.size(), 1);
    }

    @Test
    void getByTaskId_Return_Three_RelatedTask() {
        Collection<RelatedTaskEntity> collection = repository.getByTaskId(2L);

        assertEquals(collection.size(), 3);
    }

    @Test
    public void getByTaskId_Return_Empty_Collection(){
        Collection<RelatedTaskEntity> collection = repository.getByTaskId(100L);

        assertTrue(collection.isEmpty());
    }
}
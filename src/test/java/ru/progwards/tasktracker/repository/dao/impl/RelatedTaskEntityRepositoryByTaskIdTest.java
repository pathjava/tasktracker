package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RelatedTaskEntityRepositoryByTaskIdTest {

    @Autowired
    private RepositoryByTaskId<Long, RelatedTaskEntity> byTaskId;

    @Test
    void getByTaskId_Return_One_RelatedTask() {
        Collection<RelatedTaskEntity> collection = byTaskId.getByTaskId(1L);

        assertEquals(collection.size(), 1);
    }

    @Test
    void getByTaskId_Return_Three_RelatedTask() {
        Collection<RelatedTaskEntity> collection = byTaskId.getByTaskId(2L);

        assertEquals(collection.size(), 3);
    }
}
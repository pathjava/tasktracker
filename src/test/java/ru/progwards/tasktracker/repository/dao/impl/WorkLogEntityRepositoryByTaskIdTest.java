package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * тестирование получения списка логов задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogEntityRepositoryByTaskIdTest {

    @Autowired
    private RepositoryByTaskId<Long, WorkLogEntity> repository;

    @Test
    void getByTaskId() {
        Collection<WorkLogEntity> collection = repository.getByTaskId(2L);

        assertNotNull(collection);
    }

    @Test
    void getByTaskId_Return_Empty_Collection() {
        Collection<WorkLogEntity> collection = repository.getByTaskId(120L);

        assertTrue(collection.isEmpty());
    }
}
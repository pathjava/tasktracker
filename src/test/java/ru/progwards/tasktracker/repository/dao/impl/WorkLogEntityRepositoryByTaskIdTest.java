package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByTaskId;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование получения списка логов задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogEntityRepositoryByTaskIdTest {

    @Autowired
    private RepositoryByTaskId<Long, WorkLogEntity> byTaskId;

    @Autowired
    private Repository<Long, WorkLogEntity> repository;

    @Autowired
    private JsonHandler<Long, WorkLogEntity> jsonHandler;

    @Test
    void getByTaskId() {
        jsonHandler.getMap().clear();

        repository.create(
                new WorkLogEntity(null, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log RepositoryByTaskId 1", null, null)
        );
        repository.create(
                new WorkLogEntity(null, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log RepositoryByTaskId 2", null, null)
        );

        Collection<WorkLogEntity> collection = byTaskId.getByTaskId(2L);

        assertNotNull(collection);

        assertThat(collection.size(), is(2));

        List<String> actualTaskName = collection.stream()
                .map(WorkLogEntity::getDescription)
                .collect(Collectors.toUnmodifiableList());

        assertThat(actualTaskName, containsInAnyOrder("Description Log RepositoryByTaskId 1",
                "Description Log RepositoryByTaskId 2"));

        jsonHandler.getMap().clear();
    }

    @Test
    void getByTaskId_Return_Empty_Collection() {
        Collection<WorkLogEntity> collection = byTaskId.getByTaskId(anyLong());

        assertTrue(collection.isEmpty());
    }
}
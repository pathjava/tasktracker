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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование методов репозитория логов
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogEntityRepositoryTest {

    @Autowired
    private Repository<Long, WorkLogEntity> repository;

    @Autowired
    private RepositoryByTaskId<Long, WorkLogEntity> byTaskId;

    @Autowired
    private JsonHandler<Long, WorkLogEntity> jsonHandler;

    @Test
    void get() {
        createEntityForTest();

        Long id = getIdCreatedEntity();

        if (id != null) {
            WorkLogEntity entity = repository.get(id);

            assertNotNull(entity);

            assertEquals("Description Log Repository", entity.getDescription());

            repository.delete(id);
        } else
            fail();
    }

    @Test
    void get_Return_Null() {
        WorkLogEntity entity = repository.get(anyLong());

        assertNull(entity);
    }

    @Test
    void create() {
        createEntityForTest();

        Long id = getIdCreatedEntity();

        if (id != null) {
            WorkLogEntity entity = repository.get(id);

            assertNotNull(entity);

            assertEquals("Description Log Repository", entity.getDescription());

            repository.delete(id);
        } else
            fail();
    }

    @Test
    void delete() {
        createEntityForTest();

        Long id = getIdCreatedEntity();

        if (id != null) {
            repository.delete(id);

            WorkLogEntity entity = repository.get(id);

            assertNull(entity);
        } else
            fail();
    }

    private void createEntityForTest() {
        repository.create(
                new WorkLogEntity(null, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log Repository")
        );
    }

    private Long getIdCreatedEntity() {
        return byTaskId.getByTaskId(2L).stream()
                .filter(e -> e.getDescription().equals("Description Log Repository")).findFirst()
                .map(WorkLogEntity::getId)
                .orElse(null);
    }

    @Test
    void getByTaskId() {
        jsonHandler.getMap().clear();

        repository.create(
                new WorkLogEntity(null, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log RepositoryByTaskId 1")
        );
        repository.create(
                new WorkLogEntity(null, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log RepositoryByTaskId 2")
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
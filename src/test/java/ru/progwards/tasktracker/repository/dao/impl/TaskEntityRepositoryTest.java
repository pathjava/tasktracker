package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyLong;

/**
 * тестирование методов работы с репозиторием
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskEntityRepositoryTest {

    @Autowired
    private Repository<Long, TaskEntity> repository;

    @Autowired
    private JsonHandler<Long, TaskEntity> jsonHandler;

    @Test
    public void getAll() {
        createEntitiesForGetAllTest();

        Collection<TaskEntity> collection = repository.get();

        assertEquals(2, collection.size());

        assertNotNull(collection);

        List<String> list = collection.stream()
                .map(TaskEntity::getDescription)
                .collect(Collectors.toUnmodifiableList());

        assertThat(list, containsInAnyOrder("Description Repository 1",
                "Description Repository 2"));

        jsonHandler.getMap().clear();
    }

    @Test
    public void getAll_Return_Empty_Collection() {
        jsonHandler.getMap().clear();

        Collection<TaskEntity> collection = repository.get();

        assertTrue(collection.isEmpty());
    }

    @Test
    public void get() {
        TaskEntity entity = createEntityForTest();

        repository.create(entity);

        Long id = getIdCreatedEntity();

        if (id != null) {
            entity = repository.get(id);

            assertNotNull(entity);
            assertEquals("Description Repository", entity.getDescription());

            repository.delete(id);
        } else
            fail();
    }

    @Test
    public void get_Return_Null() {
        TaskEntity entity = repository.get(anyLong());

        assertNull(entity);
    }

    @Test
    public void create() {
        TaskEntity entity = createEntityForTest();

        repository.create(entity);

        Long id = getIdCreatedEntity();

        if (id != null) {
            entity = repository.get(id);

            assertNotNull(entity);
            assertEquals("Description Repository", entity.getDescription());

            repository.delete(id);
        } else
            fail();
    }

    @Test
    public void update() {
        TaskEntity entity = createEntityForTest();

        repository.create(entity);

        Long id = getIdCreatedEntity();

        if (id != null) {
            entity = new TaskEntity(
                    id, "TT1-1", "Test task", "Description Repository Updated",
                    TaskType.BUG, null, 11L, new User(), new User(),
                    ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                    null,
                    Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
            );

            repository.update(entity);

            entity = repository.get(id);

            assertNotNull(entity);

            assertEquals("Description Repository Updated", entity.getDescription());

            repository.delete(id);
        } else
            fail();
    }

    @Test
    public void delete() {
        TaskEntity entity = createEntityForTest();

        repository.create(entity);

        Long id = getIdCreatedEntity();

        if (id != null) {
            repository.delete(id);

            assertNull(repository.get(id));
        } else
            fail();
    }

    private TaskEntity createEntityForTest() {
        return new TaskEntity(
                null, "TT1-1", "Test task", "Description Repository",
                TaskType.BUG, null, 11L, new User(), new User(),
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                null,
                Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
        );
    }

    private void createEntitiesForGetAllTest() {
        jsonHandler.getMap().clear();
        repository.create(
                new TaskEntity(
                        null, "TT1-1", "Test task", "Description Repository 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
                )
        );
        repository.create(
                new TaskEntity(
                        null, "TT1-2", "Test task", "Description Repository 2",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
                )
        );
    }

    private Long getIdCreatedEntity() {
        return repository.get().stream()
                .filter(e -> e.getDescription().equals("Description Repository")).findFirst()
                .map(TaskEntity::getId)
                .orElse(null);
    }
}
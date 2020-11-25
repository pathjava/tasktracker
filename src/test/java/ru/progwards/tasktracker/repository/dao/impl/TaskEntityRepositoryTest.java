package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryByCode;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;
import ru.progwards.tasktracker.service.vo.User;

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
    private RepositoryByCode<String, TaskEntity> byCode;

    @Qualifier("taskEntityRepository")
    @Autowired
    private RepositoryUpdateField<TaskEntity> updateField;

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

//    @Test
//    public void get() {
//        TaskEntity entity = createEntityForTest();
//
//        repository.create(entity);
//
//        Long id = getIdCreatedEntity();
//
//        if (id != null) {
//            entity = repository.get(id);
//
//            assertNotNull(entity);
//            assertEquals("Description Repository", entity.getDescription());
//
//            repository.delete(id);
//        } else
//            fail();
//    }

    @Test
    public void get_Return_Null() {
        TaskEntity entity = repository.get(anyLong());

        assertNull(entity);
    }

//    @Test
//    public void create() {
//        TaskEntity entity = createEntityForTest();
//
//        repository.create(entity);
//
//        Long id = getIdCreatedEntity();
//
//        if (id != null) {
//            entity = repository.get(id);
//
//            assertNotNull(entity);
//            assertEquals("Description Repository", entity.getDescription());
//
//            repository.delete(id);
//        } else
//            fail();
//    }

    @Test
    public void update() {
//        TaskEntity entity = createEntityForTest();
//
//        repository.create(entity);
//
//        Long id = getIdCreatedEntity();
//
//        if (id != null) {
//            entity = new TaskEntity(
//                    id, "TT1-1", "Test task", "Description Repository Updated",
//                    null, null, 11L, null, null,
//                    ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
//                    null,
//                    Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
//                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
//            );
//
//            repository.update(entity);
//
//            entity = repository.get(id);
//
//            assertNotNull(entity);
//
//            assertEquals("Description Repository Updated", entity.getDescription());
//
//            repository.delete(id);
//        } else
//            fail();
    }

//    @Test
//    public void delete() {
//        TaskEntity entity = createEntityForTest();
//
//        repository.create(entity);
//
//        Long id = getIdCreatedEntity();
//
//        if (id != null) {
//            repository.delete(id);
//
//            assertNull(repository.get(id));
//        } else
//            fail();
//    }

//    private TaskEntity createEntityForTest() {
//        return new TaskEntity(
//                null, "TT1-1", "Test task", "Description Repository",
//                null, null, 11L, null, null,
//                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
//                null,
//                Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
//                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
//        );
//    }

    private void createEntitiesForGetAllTest() {
//        jsonHandler.getMap().clear();
//        repository.create(
//                new TaskEntity(
//                        null, "TT1-1", "Test task", "Description Repository 1",
//                        null, null, 11L, null, null,
//                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
//                        null,
//                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
//                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
//                )
//        );
//        repository.create(
//                new TaskEntity(
//                        null, "TT1-2", "Test task", "Description Repository 2",
//                        null, null, 11L, null, null,
//                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
//                        null,
//                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
//                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
//                )
//        );
    }

    private Long getIdCreatedEntity() {
        return repository.get().stream()
                .filter(e -> e.getDescription().equals("Description Repository")).findFirst()
                .map(TaskEntity::getId)
                .orElse(null);
    }

    @Test
    void getByProjectId() { //TODO - to do
    }

    @Test
    public void updateField() {
//        repository.create(
//                new TaskEntity(
//                        null, "TT1-1", "Test task", "Description RepositoryUpdateField",
//                        null, null, 11L, null, null,
//                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
//                        null,
//                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
//                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
//                )
//        );
//
//        Long id = repository.get().stream()
//                .filter(e -> e.getDescription().equals("Description RepositoryUpdateField")).findFirst()
//                .map(TaskEntity::getId)
//                .orElse(null);
//
//        assertEquals("Description RepositoryUpdateField", repository.get(id).getDescription());
//
//        updateField.updateField(new UpdateOneValue(
//                id, "Description RepositoryUpdateField Updated", "description"
//        ));
//
//        assertEquals("Description RepositoryUpdateField Updated", repository.get(id).getDescription());
//
//        repository.delete(id);
    }

    @Test
    void getByCode() {
//        jsonHandler.getMap().clear();
//
//        repository.create(
//                new TaskEntity(
//                        null, "TT100-1", "Test task", "Description Repository",
//                        null, null, 11L, null, null,
//                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
//                        null,
//                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
//                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
//                )
//        );
//
//        TaskEntity entity = byCode.getByCode("TT100-1");
//
//        assertNotNull(entity);
//
//        assertEquals("Test task", entity.getName());
//
//        Long id = entity.getId();
//
//        if (id != null)
//            repository.delete(id);
//        else
//            fail();
    }
}
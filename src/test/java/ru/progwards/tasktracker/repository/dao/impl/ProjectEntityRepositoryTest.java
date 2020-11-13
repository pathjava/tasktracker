package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
public class ProjectEntityRepositoryTest {

    @InjectMocks
    private ProjectEntityRepository repository;

    @Mock
    private JsonHandler<Long, ProjectEntity> projectEntityJsonHandler;

    private Map<Long, ProjectEntity> map;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEntitiesTest() {
        map = new ConcurrentHashMap<>();

        for (long i = 0; i < 10; i++) {
            map.put(i,new ProjectEntity(i, "name"+i, "description"+i, "", i, 1000L, i, 0L));
        }

        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);

        Assertions.assertEquals(map.values().size(), repository.get().size());
    }

    @Test
    public void getEntityTest() {
        map = new ConcurrentHashMap<>();

        for (long i = 0; i < 10; i++) {
            map.put(i,new ProjectEntity(i, "name"+i, "description"+i, "", i, 1000L, i, 0L));
        }

        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);

        ProjectEntity entity = map.get(1L);

        Assertions.assertEquals(entity, repository.get(entity.getId()));
    }

    @Test
    public void createTest() {
        for (long i = 0; i < 10; i++) {
            repository.create(new ProjectEntity(i, "name"+i, "description"+i, "", i, 1000L, i, 0L));
        }

        Mockito.verify(projectEntityJsonHandler, Mockito.times(10)).write();
    }

    @Test
    public void updateTest() {
        map = new ConcurrentHashMap<>();

        for (long i = 0; i < 10; i++) {
            map.put(i,new ProjectEntity(i, "name"+i, "description"+i, "", i, 1000L, i, 0L));
        }

        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);

        ProjectEntity entity = projectEntityJsonHandler.getMap().get(1L);
        String newName = entity.getName() + 23;

        entity.setName(newName);

        repository.update(entity);

        ProjectEntity entityAfter = projectEntityJsonHandler.getMap().get(1L);
        String nameAfterUpdate = entityAfter.getName();

        Assertions.assertEquals(nameAfterUpdate, newName);
    }

    @Test
    public void deleteTest() {
        map = new ConcurrentHashMap<>();

        for (long i = 0; i < 10; i++) {
            map.put(i,new ProjectEntity(i, "name"+i, "description"+i, "", i, 1000L, i, 0L));
        }

        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(map);

        ProjectEntity entity = repository.get(3L);

        Assertions.assertNotNull(entity);

        repository.delete(entity.getId());

        ProjectEntity entityAfterDelete = repository.get(3L);

        Assertions.assertNull(entityAfterDelete);
    }
}
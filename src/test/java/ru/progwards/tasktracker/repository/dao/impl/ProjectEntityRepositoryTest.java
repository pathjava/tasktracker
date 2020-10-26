package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest
public class ProjectEntityRepositoryTest {
//    @InjectMocks
//    private ProjectEntityRepository repository;
//    @Mock
//    private JsonHandlerProjectEntity jsonHandlerProjectEntity;

    @Autowired
    private ProjectEntityRepository repositoryAuto;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEntitiesTest() {
        Collection<ProjectEntity> entityList = new ArrayList<>();

        for (long i = 0; i < 10; i++) {
            entityList.add(new ProjectEntity(i, "name"+i, "desc"+i, "", i, 1000L, i, 0L));
        }

        entityList.forEach(e -> repositoryAuto.create(e));

        Collection<ProjectEntity> result = repositoryAuto.get();

        Assertions.assertNotNull(result);

        Assertions.assertEquals(entityList, result);
    }

    @Test
    public void getEntityTest() {
        ProjectEntity entity = new ProjectEntity(1L, "name1", "desc1", "",1L, 1000L, 1L, 0L);
        repositoryAuto.create(entity);

        Assertions.assertEquals(entity, repositoryAuto.get(entity.getId()));
    }

    @Test
    public void createTest() {
//        for (long i = 0; i < 10; i++) {
//            repository.create(new ProjectEntity(i, "name"+i, "description"+i, "", i, 1000L, i, 0L));
//        }
//        Mockito.verify(jsonHandlerProjectEntity, Mockito.times(10)).write();

        ProjectEntity entity = new ProjectEntity(1L, "name1", "desc1","",1L, 1000L, 1L, 0L);
        repositoryAuto.create(entity);
        Assertions.assertNotNull(repositoryAuto.get(entity.getId()));
    }

    @Test
    public void updateTest() {
        ProjectEntity entityBefore = new ProjectEntity(1L, "name1", "desc1", "", 1L, 1000L, 1L, 0L);
        repositoryAuto.create(entityBefore);
        String nameBefore = repositoryAuto.get(entityBefore.getId()).getName();

        ProjectEntity entityAfter = new ProjectEntity(1L, "name123", "desc1", "", 1L, 1000L, 1L, 0L);
        repositoryAuto.update(entityAfter);
        String nameAfter = repositoryAuto.get(entityAfter.getId()).getName();

        Assertions.assertEquals(nameAfter, nameBefore + "23");
    }

    @Test
    public void deleteTest() {
        ProjectEntity entity = new ProjectEntity(1L, "name1", "desc1", "", 1L, 1000L, 1L, 0L);
        repositoryAuto.create(entity);
        Assertions.assertNotNull(repositoryAuto.get(entity.getId()));

        repositoryAuto.delete(entity.getId());
        Assertions.assertNull(repositoryAuto.get(entity.getId()));
    }
}

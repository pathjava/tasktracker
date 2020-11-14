package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
public class ProjectEntityRepositoryUpdateFieldTest {

    @Mock
    private JsonHandler<Long, ProjectEntity> projectEntityJsonHandler;

    /* в объект repository будет инжектиться объект projectEntityJsonHandler,
    * а repository будет инжектиться в объект projectEntityRepositoryUpdateField
    * (данную схему подсмотрел на StackOverFlow)
    * */
    @InjectMocks
    private ProjectEntityRepository repository = Mockito.spy(ProjectEntityRepository.class);

    @InjectMocks
    private ProjectEntityRepositoryUpdateField projectEntityRepositoryUpdateField;

    /**
     * сравниваем значение поля name у ProjectEntity до изменения со значением после изменения
     */
    @Test
    public void updateFieldTest() {
        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(new ConcurrentHashMap<>());

        ProjectEntity entity = new ProjectEntity(1L, "name1", "desc1", "", 1L, 1000L, 1L, 0L);
        repository.create(entity);

        String beforeUpdateName = repository.get(entity.getId()).getName();
        UpdateOneValue updateOneValue = new UpdateOneValue(entity.getId(), "name1777", "name");
        projectEntityRepositoryUpdateField.updateField(updateOneValue);
        String afterUpdateName = repository.get(entity.getId()).getName();

        Assertions.assertEquals(afterUpdateName, beforeUpdateName + "777");
    }
}
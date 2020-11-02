package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

@SpringBootTest
public class ProjectEntityRepositoryUpdateFieldTest {

    @Autowired
    private RepositoryUpdateField<ProjectEntity> projectEntityRepositoryUpdateField;

    @Autowired
    private Repository<Long, ProjectEntity> repository;

    /**
     * сравниваем значение поля name у ProjectEntity до изменения со значением после изменения
     */
    @Test
    public void updateFieldTest() {
        ProjectEntity entity = new ProjectEntity(1L, "name1", "desc1", "", 1L, 1000L, 1L, 0L);
        repository.create(entity);
        String beforeUpdateName = repository.get(entity.getId()).getName();

        UpdateOneValue updateOneValue = new UpdateOneValue(entity.getId(), "name1777", "name");
        projectEntityRepositoryUpdateField.updateField(updateOneValue);
        String afterUpdateName = repository.get(entity.getId()).getName();

        Assertions.assertEquals(afterUpdateName, beforeUpdateName + "777");
    }
}

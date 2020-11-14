package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.RepositoryUpdateField;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * тестирование метода обновления поля задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskEntityRepositoryUpdateFieldTest {

    @Autowired
    private Repository<Long, TaskEntity> repository;

    @Qualifier("taskEntityRepositoryUpdateField")
    @Autowired
    private RepositoryUpdateField<TaskEntity> updateField;

    @Test
    public void updateField() {
        repository.create(
                new TaskEntity(
                        null, "TT1-1", "Test task", "Description RepositoryUpdateField",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
                )
        );

        Long id = repository.get().stream()
                .filter(e -> e.getDescription().equals("Description RepositoryUpdateField")).findFirst()
                .map(TaskEntity::getId)
                .orElse(null);

        assertEquals("Description RepositoryUpdateField", repository.get(id).getDescription());

        updateField.updateField(new UpdateOneValue(
                id, "Description RepositoryUpdateField Updated", "description"
        ));

        assertEquals("Description RepositoryUpdateField Updated", repository.get(id).getDescription());

        repository.delete(id);
    }
}
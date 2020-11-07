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
    public void testUpdateField() {
        repository.create(
                new TaskEntity(1L, "TT1-1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );

        assertEquals("TT1-1", repository.get(1L).getCode());

        updateField.updateField(new UpdateOneValue(1L, "TT1-1-1", "code"));
        String str = repository.get(1L).getCode();
        assertEquals("TT1-1-1", repository.get(1L).getCode());
    }
}
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

import static org.junit.jupiter.api.Assertions.*;

/**
 * тестирование получения задачи по текстовому коду задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskEntityByCodeRepositoryTest {

    @Autowired
    private Repository<String, TaskEntity> repositoryByCode;

    @Autowired
    private Repository<Long, TaskEntity> repository;

    @Autowired
    private JsonHandler<Long, TaskEntity> jsonHandler;

    @Test
    void get() {
        jsonHandler.getMap().clear();

        repository.create(
                new TaskEntity(
                        null, "TT100-1", "Test task", "Description Repository",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false
                )
        );

        TaskEntity entity = repositoryByCode.get("TT100-1");

        assertNotNull(entity);

        assertEquals("Test task", entity.getName());

        Long id = entity.getId();

        if (id != null)
            repository.delete(id);
        else
            fail();
    }
}
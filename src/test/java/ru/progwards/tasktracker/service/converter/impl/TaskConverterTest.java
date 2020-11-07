package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.Converter;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * тестирование конвертера между valueObject <-> entity
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskConverterTest {

    @Autowired
    private Converter<TaskEntity, Task> converter;

    @Test
    void toVo_return_Null() {
        Task tempTask = converter.toVo(null);

        assertThat(tempTask, is(nullValue()));
    }

    @Test
    void toVo_return_Not_Null() {
        Task tempTask = converter.toVo(
                new TaskEntity(1L, "TT1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        null,
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );

        assertThat(tempTask, is(notNullValue()));
    }

    @Test
    void toEntity_return_Null() {
        TaskEntity tempTaskEntity = converter.toEntity(null);

        assertThat(tempTaskEntity, is(nullValue()));
    }

    @Test
    void toEntity_return_Not_Null() {
        TaskEntity tempTaskEntity = converter.toEntity(
                new Task(1L, "TT1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );

        assertThat(tempTaskEntity, is(notNullValue()));
    }
}
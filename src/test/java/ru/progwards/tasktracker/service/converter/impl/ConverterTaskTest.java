package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class ConverterTaskTest {

    @Autowired
    private ConverterTask converterTask;

    @Test
    void toVo_return_Null() {
        Task tempTask = converterTask.toVo(null);

        assertThat(tempTask, is(nullValue()));
    }

    @Test
    void toVo_return_Not_Null() {
        Task tempTask = converterTask.toVo(
                new TaskEntity(1L, "TT1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3).toSeconds(), Duration.ofDays(1).toSeconds(), Duration.ofDays(2).toSeconds(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        );

        assertThat(tempTask, is(notNullValue()));
    }

    @Test
    void toEntity_return_Null() {
        TaskEntity tempTaskEntity = converterTask.toEntity(null);

        assertThat(tempTaskEntity, is(nullValue()));
    }

    @Test
    void toEntity_return_Not_Null() {
        TaskEntity tempTaskEntity = converterTask.toEntity(
                new Task(1L, "TT1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),false)
        );

        assertThat(tempTaskEntity, is(notNullValue()));
    }
}
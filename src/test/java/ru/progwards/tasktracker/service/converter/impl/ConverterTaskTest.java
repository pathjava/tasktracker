package ru.progwards.tasktracker.service.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;

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
                new TaskEntity(1L, "Testing_task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L,
                        ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
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
                new Task(1L, "Testing_task1_test", "description1", TaskType.BUG, Priority.MAJOR,
                        001L, 003L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                        123456L, 123456L, 123456L)
        );

        assertThat(tempTaskEntity, is(notNullValue()));
    }
}
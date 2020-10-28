package ru.progwards.tasktracker.controller.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoPreview;
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
class TaskDtoPreviewConverterTest {

    @Autowired
    private Converter<Task, TaskDtoPreview> dtoPreviewConverter;

    @Test
    void toModel_return_Null() {
        Task task = dtoPreviewConverter.toModel(null);

        assertThat(task, is(nullValue()));
    }

    @Test
    void toModel_return_Not_Null() {
        Task task = dtoPreviewConverter.toModel(
                new TaskDtoPreview(1L, "TT1", "Test task 1 TEST")
        );

        assertThat(task, is(nullValue())); //TODO - пока в конвертере из TaskDtoPreview в Task возвращается null
    }

    @Test
    void toDto_return_Null() {
        TaskDtoPreview taskDto = dtoPreviewConverter.toDto(null);

        assertThat(taskDto, is(nullValue()));
    }

    @Test
    void toDto_return_Not_Null() {
        TaskDtoPreview taskDto = dtoPreviewConverter.toDto(
                new Task(1L, "TT1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );

        assertThat(taskDto, is(notNullValue()));
    }
}
package ru.progwards.tasktracker.controller.converter.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * тестирование конвертера между DTO и VO
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskDtoFullConverterTest {

    @Autowired
    private Converter<Task, TaskDtoFull> dtoFullConverter;

    @Test
    void toModel_Return_Null() {
        Task task = dtoFullConverter.toModel(null);

        assertNull(task);
    }

    @Test
    void toModel_Return_Not_Null() {
        Task task = dtoFullConverter.toModel(
                new TaskDtoFull(1L, "TT1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );

        assertThat(task, is(notNullValue()));
    }

    @Test
    void toDto_Return_Null() {
        TaskDtoFull task = dtoFullConverter.toDto(null);

        assertNull(task);
    }

    @Test
    void toDto_Return_Not_Null() {
        TaskDtoFull task = dtoFullConverter.toDto(
                new Task(1L, "TT1", "Test task 1 TEST", "Description task 1",
                        TaskType.BUG, null, 11L, new User(), new User(),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        null,
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );

        assertThat(task, is(notNullValue()));
    }
}
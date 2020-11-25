package ru.progwards.tasktracker.controller.converter.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.TaskType;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * тестирование конвертера между valueObject <-> dto
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class TaskDtoFullConverterTest {

    @Mock
    private Converter<Task, TaskDtoFull> converter;

    private final TaskDtoFull taskDtoFull = mock(TaskDtoFull.class);

    private final Task task = mock(Task.class);

    @Test
    void toModel_Return_Null() {
        Task task = converter.toModel(null);

        assertNull(task);
    }

    @Test
    void toModel_Return_Not_Null() {
        Task task = converter.toModel(taskDtoFull);

        assertThat(task, instanceOf(Task.class));
    }

    @Test
    void toDto_Return_Null() {
        TaskDtoFull task = converter.toDto(null);

        assertNull(task);
    }

    @Test
    void toDto_Return_Not_Null() {
        TaskDtoFull taskDtoFull = converter.toDto(task);

        assertThat(taskDtoFull, is(notNullValue()));
    }
}
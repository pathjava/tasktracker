package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.TaskNotFoundException;
import ru.progwards.tasktracker.service.facade.impl.TaskByCodeGetService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.util.types.TaskPriority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkFlowStatus;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskGetByCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskGetByCodeController getByCodeController;

    @Autowired
    private TaskByCodeGetService byCodeGetService;

    @Autowired
    private TaskController taskController;

    @Test
    void controllerIsNotNull() {
        assertThat(getByCodeController, is(notNullValue()));
    }

    @Test
    void getTaskByCode() throws Exception {
        boolean add = taskController.addTask(
                new Task(10L, "TT10-1", "Test task 10", "Description task 10",
                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                        new WorkFlowStatus(11L),
                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false)
        ).getStatusCode().is2xxSuccessful();

        assertTrue(add);

        Task task = byCodeGetService.get("TT10-1");

        String jsonString = new ObjectMapper()
                .registerModule(new JavaTimeModule()).writeValueAsString(task);

        mockMvc.perform(get("/tt/browse/TT10-1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    @Test
    void getTaskByCode_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> getByCodeController.getTaskByCode(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test
    void getTaskByCode_TaskNotFoundException() {
        Exception exception = assertThrows(TaskNotFoundException.class,
                () -> getByCodeController.getTaskByCode("TT10-11"));
        assertTrue(exception.getMessage().contains(" не найдена!"));
    }
}
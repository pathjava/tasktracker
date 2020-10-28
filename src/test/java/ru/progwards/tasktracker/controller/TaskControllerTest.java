package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskDtoFull;
import ru.progwards.tasktracker.controller.dto.TaskDtoPreview;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.TaskNotExistException;
import ru.progwards.tasktracker.controller.exception.TaskNotFoundException;
import ru.progwards.tasktracker.controller.exception.TasksNotFoundException;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskController taskController;

    @Autowired
    private GetService<Long, Task> taskGetService;

    @Autowired
    private GetListService<Task> taskGetListService;

    @Autowired
    private GetService<String, Task> byCodeGetService;

    @Autowired
    private Converter<Task, TaskDtoPreview> dtoConverter;

    @Autowired
    private Converter<Task, TaskDtoFull> dtoFullConverter;

    @Test
    public void testController() {
        assertThat(taskController, is(notNullValue()));
    }

    @Test
    void getTaskById() throws Exception {
        TaskDtoPreview tempTask = dtoConverter.toDto(taskGetService.get(1L));

        String jsonString = new ObjectMapper()
                .registerModule(new JavaTimeModule()).writeValueAsString(tempTask);

        mockMvc.perform(get("/rest/project/2/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    @Test()
    void getTaskByID_BadRequestException() {
//        BadRequestException bre = new BadRequestException("111");
//        doThrow(bre).when(taskController).getTask(null); /* taskController - @Mock */

        Exception exception = assertThrows(BadRequestException.class,
                () -> taskController.getTask(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test()
    void getTaskByID_TaskNotFoundException() {
        Exception exception = assertThrows(TaskNotFoundException.class,
                () -> taskController.getTask(20L));
        assertTrue(exception.getMessage().contains(" не найдена!"));
    }

    @Test
    void getAllProjectTasks() throws Exception {
        Collection<TaskDtoPreview> tempTasks = taskGetListService.getList().stream()
                .filter(task -> task.getProject_id().equals(2L))
                .map(task -> dtoConverter.toDto(task))
                .collect(Collectors.toList());

        String jsonString = new ObjectMapper()
                .registerModule(new JavaTimeModule()).writeValueAsString(tempTasks);

        mockMvc.perform(get("/rest/project/2/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    @Test()
    void getAllProjectTasks_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> taskController.getAllTasks(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test()
    void getAllProjectTasks_TasksNotFoundException() {
        Exception exception = assertThrows(TasksNotFoundException.class,
                () -> taskController.getAllTasks(20L));
        assertTrue(exception.getMessage().contains("Список задач пустой!"));
    }

    @Test
    void addTask() throws Exception {
//        boolean add = taskController.addTask(
//                new Task(10L, "TT10-1", "Test task 10", "Description task 10",
//                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
//                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
//                        new WorkFlowStatus(11L),
//                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
//                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
//        ).getStatusCode().is2xxSuccessful();
//
//        assertTrue(add);

        mockMvc.perform(post("/rest/project/2/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 10,\n" +
                                "    \"code\": \"TT10-1\",\n" +
                                "    \"name\": \"Test task 10\",\n" +
                                "    \"description\": \"Description task 10\",\n" +
                                "    \"type\": \"BUG\",\n" +
                                "    \"priority\": \"MAJOR\",\n" +
                                "    \"project_id\": 2,\n" +
                                "    \"author\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"executor\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"created\": 1603274345,\n" +
                                "    \"updated\": null,\n" +
                                "    \"status\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"estimation\": 259200,\n" +
                                "    \"timeSpent\": null,\n" +
                                "    \"timeLeft\": null,\n" +
                                "    \"relatedTasks\": [],\n" +
                                "    \"attachments\": [],\n" +
                                "    \"workLogs\": []\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        mockMvc.perform(get("/rest/project/2/tasks/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(10)))
                .andExpect(jsonPath("$.name", equalTo("Test task 10")));
    }

    @Test()
    void addTask_TaskNotExistException() {
        Exception exception = assertThrows(TaskNotExistException.class,
                () -> taskController.addTask(null));
        assertTrue(exception.getMessage().contains("Задача не существует!"));
    }

    @Test
    void updateTask() throws Exception {
//        boolean add = taskController.addTask(
//                new Task(11L, "TT11-1", "Test task 11 updated", "Description task 11",
//                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
//                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
//                        new WorkFlowStatus(11L),
//                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
//                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
//        ).getStatusCode().is2xxSuccessful();
//
//        assertTrue(add);

        mockMvc.perform(put("/rest/project/2/tasks/11/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 11,\n" +
                                "    \"code\": \"TT11-1\",\n" +
                                "    \"name\": \"Test task 11 updated\",\n" +
                                "    \"description\": \"Description task 11\",\n" +
                                "    \"type\": \"BUG\",\n" +
                                "    \"priority\": \"MAJOR\",\n" +
                                "    \"project_id\": 2,\n" +
                                "    \"author\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"executor\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"created\": 1603274345,\n" +
                                "    \"updated\": null,\n" +
                                "    \"status\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"estimation\": 259200,\n" +
                                "    \"timeSpent\": null,\n" +
                                "    \"timeLeft\": null,\n" +
                                "    \"relatedTasks\": [],\n" +
                                "    \"attachments\": [],\n" +
                                "    \"workLogs\": []\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        mockMvc.perform(get("/rest/project/2/tasks/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(11)))
                .andExpect(jsonPath("$.name", equalTo("Test task 11 updated")));
    }

    @Test()
    void updateTask_TaskNotExistException() {
        Exception exception = assertThrows(TaskNotExistException.class,
                () -> taskController.updateTask(null));
        assertTrue(exception.getMessage().contains("Задача не существует!"));
    }

    @Test
    void deleteTaskById() throws Exception {
        mockMvc.perform(delete("/rest/project/2/tasks/{id}/delete", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/rest/project/2/tasks/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test()
    void deleteTaskById_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> taskController.deleteTask(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test()
    void deleteTaskById_TaskNotFoundException() {
        Exception exception = assertThrows(TaskNotFoundException.class,
                () -> taskController.deleteTask(20L));
        assertTrue(exception.getMessage().contains(" не найдена!"));
    }

    @Test
    void getTaskByCode() throws Exception {
//        boolean add = taskController.addTask(
//                new Task(10L, "TT10-1", "Test task 10", "Description task 10",
//                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(11L), new User(11L),
//                        ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
//                        new WorkFlowStatus(11L),
//                        Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
//                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
//        ).getStatusCode().is2xxSuccessful();
//
//        assertTrue(add);

        mockMvc.perform(post("/rest/project/2/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 10,\n" +
                                "    \"code\": \"TT10-1\",\n" +
                                "    \"name\": \"Test task 10\",\n" +
                                "    \"description\": \"Description task 10\",\n" +
                                "    \"type\": \"BUG\",\n" +
                                "    \"priority\": \"MAJOR\",\n" +
                                "    \"project_id\": 2,\n" +
                                "    \"author\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"executor\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"created\": 1603274345,\n" +
                                "    \"updated\": null,\n" +
                                "    \"status\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"estimation\": 259200,\n" +
                                "    \"timeSpent\": null,\n" +
                                "    \"timeLeft\": null,\n" +
                                "    \"relatedTasks\": [],\n" +
                                "    \"attachments\": [],\n" +
                                "    \"workLogs\": []\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        TaskDtoFull task = dtoFullConverter.toDto(byCodeGetService.get("TT10-1"));

        String jsonString = new ObjectMapper()
                .registerModule(new JavaTimeModule()).writeValueAsString(task);

        mockMvc.perform(get("/tt/browse/TT10-1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    @Test
    void getTaskByCode_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> taskController.getTaskByCode(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test
    void getTaskByCode_TaskNotFoundException() {
        Exception exception = assertThrows(TaskNotFoundException.class,
                () -> taskController.getTaskByCode("TT10-11"));
        assertTrue(exception.getMessage().contains(" не найдена!"));
    }
}
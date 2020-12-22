package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskPriorityDtoFull;
import ru.progwards.tasktracker.repository.deprecated.Repository;
import ru.progwards.tasktracker.repository.deprecated.entity.TaskPriorityEntity;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.TaskPriority;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Тест TaskPriorityController
 * @author Pavel Khovaylo
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TaskPriorityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskPriorityController controller;

    @Autowired
    private Repository<Long, TaskPriorityEntity> repository;

    @Autowired
    private Converter<TaskPriority, TaskPriorityDtoFull> converter;

    @Autowired
    private GetListService<TaskPriority> taskPriorityGetListService;

    @Autowired
    private GetService<Long, TaskPriority> taskPriorityGetService;

    @Autowired
    private CreateService<TaskPriority> taskPriorityCreateService;

    @Autowired
    private RefreshService<TaskPriority> taskPriorityRefreshService;

    @Autowired
    private RemoveService<TaskPriority> taskPriorityRemoveService;

    @Test
    public void loadedController() {
        Assertions.assertNotNull(controller);
    }

    @Test
    public void getTaskPrioritiesTest() throws Exception {
        Collection<TaskPriorityDtoFull> taskPriorityDtoFulls = taskPriorityGetListService.getList().stream().
                map(e -> converter.toDto(e)).collect(Collectors.toList());

        String stringJson = new ObjectMapper().writeValueAsString(taskPriorityDtoFulls);

        mockMvc.perform(get("/rest/task-priority/list")).
                andExpect(status().isOk()).
                andExpect(content().json(stringJson));
    }

    @Test
    public void getTaskPriorityTest() throws Exception {
        TaskPriorityDtoFull taskPriorityDtoFull = converter.toDto(taskPriorityGetService.get(1L));

        String stringJson = new ObjectMapper().writeValueAsString(taskPriorityDtoFull);

        mockMvc.perform(get("/rest/task-priority/1")).
                andExpect(status().isOk()).
                andExpect(content().json(stringJson));
    }

    @Test
    public void createTest() throws Exception {
        TaskPriorityDtoFull taskPriorityDtoFull = new TaskPriorityDtoFull(6L, "name6", 6);

        String stringJson = new ObjectMapper().writeValueAsString(taskPriorityDtoFull);

        mockMvc.perform(post("/rest/task-priority/create").
                content(
                   "{\n" +
                   "    \"id\": 6,\n" +
                   "    \"name\": \"name6\",\n" +
                   "    \"value\": 6\n" +
                   "}"
                ).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().is2xxSuccessful()).andExpect(content().json(stringJson));

        mockMvc.perform(get("/rest/task-priority/6")).
                andExpect(jsonPath("$.name").value("name6")).
                andExpect(jsonPath("$.value").value("6"));
    }

    @Test
    public void updateTest() throws Exception {
        String newName = "name of project 6";

        mockMvc.perform(post("/rest/task-priority/6/update").
                content(
                        "{\n" +
                        "    \"name\": \"name of project 6\",\n" +
                        "    \"value\": 6\n" +
                        "}"
                ).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isOk());

        mockMvc.perform(get("/rest/task-priority/6")).
                andExpect(jsonPath("$.name").value(newName));
    }

    @Test
    public void deleteTest() throws Exception {
        TaskPriorityEntity entity = repository.get(6L);

        if (entity != null)
            mockMvc.perform(post("/rest/task-priority/6/delete")).andExpect(status().isOk());
    }
}

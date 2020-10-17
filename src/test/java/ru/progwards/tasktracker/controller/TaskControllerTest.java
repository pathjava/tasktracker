package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.service.facade.impl.TaskGetListService;
import ru.progwards.tasktracker.service.facade.impl.TaskGetService;
import ru.progwards.tasktracker.service.vo.Task;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
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
    private TaskGetService taskGetService;

    @Autowired
    private TaskGetListService taskGetListService;

    @Test
    public void testController() {
        assertThat(taskController, is(notNullValue()));
    }

    @Test
    void getTask() throws Exception {
        Task tempTask = taskGetService.get(1L);
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        String jsonString = mapper.writeValueAsString(tempTask);

        mockMvc.perform(get("/rest/task/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    @Test
    void getAllTasks() throws Exception {
        Collection<Task> tempTasks = taskGetListService.getList();
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        String jsonString = mapper.writeValueAsString(tempTasks);

        mockMvc.perform(get("/rest/task/get/"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    @Test
    void addTask() throws Exception {
        mockMvc.perform(post("/rest/task/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 10,\n" +
                                "    \"code\": \"TT10-1\",\n" +
                                "    \"name\": \"Test task 10\",\n" +
                                "    \"description\": \"Description task 10\",\n" +
                                "    \"type\": \"BUG\",\n" +
                                "    \"priority\": \"MAJOR\",\n" +
                                "    \"project\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"author\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"executor\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"created\": 1603274345,\n" +
                                "    \"updated\": 1603360745,\n" +
                                "    \"status\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"estimation\": 259200,\n" +
                                "    \"timeSpent\": 86400,\n" +
                                "    \"timeLeft\": 172800,\n" +
                                "    \"relatedTasks\": [],\n" +
                                "    \"attachments\": [],\n" +
                                "    \"workLogs\": []\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/rest/task/get/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(10)))
                .andExpect(jsonPath("$.name", equalTo("Test task 10")));
    }

    @Test
    void updateTask() throws Exception {
        mockMvc.perform(put("/rest/task/update/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 11,\n" +
                                "    \"code\": \"TT11-1\",\n" +
                                "    \"name\": \"Test task 11 updated\",\n" +
                                "    \"description\": \"Description task 11\",\n" +
                                "    \"type\": \"BUG\",\n" +
                                "    \"priority\": \"MAJOR\",\n" +
                                "    \"project\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"author\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"executor\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"created\": 1603274345,\n" +
                                "    \"updated\": 1603360745,\n" +
                                "    \"status\": {\n" +
                                "      \"id\": 1\n" +
                                "    },\n" +
                                "    \"estimation\": 259200,\n" +
                                "    \"timeSpent\": 86400,\n" +
                                "    \"timeLeft\": 172800,\n" +
                                "    \"relatedTasks\": [],\n" +
                                "    \"attachments\": [],\n" +
                                "    \"workLogs\": []\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/rest/task/get/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(11)))
                .andExpect(jsonPath("$.name", equalTo("Test task 11 updated")));
    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/rest/task/delete/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/rest/task/get/1"))
                .andExpect(status().is4xxClientError());
    }
}
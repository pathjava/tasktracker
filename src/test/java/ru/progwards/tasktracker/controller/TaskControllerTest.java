package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.service.facade.impl.*;
import ru.progwards.tasktracker.service.vo.Task;

import java.text.SimpleDateFormat;
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
                .content("{\"id\":8,\"code\":\"TT8-1\",\"name\":\"Test task 8\",\"description\":\"Description task 8\",\"type\":\"BUG\",\"priority\":\"MAJOR\",\"project\":{\"id\":1},\"author\":{\"id\":1},\"executor\":{\"id\":1},\"created\":\"2020-10-16T20:36:49+03:00\",\"updated\":\"2020-10-17T20:36:49+03:00\",\"status\":{\"id\":1},\"estimation\":\"PT72H\",\"timeSpent\":\"PT24H\",\"timeLeft\":\"PT48H\",\"relatedTasks\":[],\"attachments\":[],\"workLogs\":[]}"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/rest/task/get/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(8)))
                .andExpect(jsonPath("$.name", equalTo("Test task 8")));
    }

    @Test
    void updateTask() throws Exception {
        mockMvc.perform(put("/rest/task/update/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":8,\"code\":\"TT8-1\",\"name\":\"Test task 8 updated\",\"description\":\"Description task 8\",\"type\":\"BUG\",\"priority\":\"MAJOR\",\"project\":{\"id\":1},\"author\":{\"id\":1},\"executor\":{\"id\":1},\"created\":\"2020-10-16T20:36:49+03:00\",\"updated\":\"2020-10-17T20:36:49+03:00\",\"status\":{\"id\":1},\"estimation\":\"PT72H\",\"timeSpent\":\"PT24H\",\"timeLeft\":\"PT48H\",\"relatedTasks\":[],\"attachments\":[],\"workLogs\":[]}"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/rest/task/get/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(8)))
                .andExpect(jsonPath("$.name", equalTo("Test task 8 updated")));
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
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .registerModule(new JavaTimeModule())
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
        String jsonString = mapper.writeValueAsString(tempTask);

        mockMvc.perform(get("/rest/task/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    @Test
    void getAllTasks() throws Exception {
        Collection<Task> tempTasks = taskGetListService.getList();
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));
        String jsonString = mapper.writeValueAsString(tempTasks);

        mockMvc.perform(get("/rest/task/get/"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    @Test
    void addTask() throws Exception {
        mockMvc.perform(post("/rest/task/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":8,\"name\":\"task8_test\",\"description\":\"description1\",\"type\":\"BUG\",\"priority\":\"MAJOR\",\"authorUserId\":1,\"executorUserId\":3,\"created\":\"2020-10-13T12:55:00+03:00\",\"updated\":\"2020-10-14T12:55:00+03:00\",\"storyPoint\":100,\"projectId\":5,\"strCode\":\"STR_CODE_TTT\",\"wfStatus\":\"NEW\",\"version\":\"new_version\",\"planDuration\":123456,\"spentDuration\":123456,\"leftDuration\":123456}"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateTask() throws Exception {
        mockMvc.perform(put("/rest/task/update/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":8,\"name\":\"task8_test\",\"description\":\"description1\",\"type\":\"BUG\",\"priority\":\"MAJOR\",\"authorUserId\":1,\"executorUserId\":3,\"created\":\"2020-10-13T12:55:00+03:00\",\"updated\":\"2020-10-14T12:55:00+03:00\",\"storyPoint\":100,\"projectId\":5,\"strCode\":\"STR_CODE_TTT\",\"wfStatus\":\"NEW\",\"version\":\"new_version\",\"planDuration\":123456,\"spentDuration\":123456,\"leftDuration\":123456}"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/rest/task/delete/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}
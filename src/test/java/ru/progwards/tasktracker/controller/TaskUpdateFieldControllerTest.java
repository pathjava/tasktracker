package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskUpdateFieldControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TaskUpdateFieldController updateFieldController;
//
//    @BeforeAll
//    public void createTestObject() throws Exception{
//        mockMvc.perform(post("/rest/task/add/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"id\":28,\"name\":\"task8_test\",\"description\":\"description1\",\"type\":\"BUG\",\"priority\":\"MAJOR\",\"authorUserId\":1,\"executorUserId\":3,\"created\":\"2020-10-13T12:55:00+03:00\",\"updated\":\"2020-10-14T12:55:00+03:00\",\"storyPoint\":100,\"projectId\":5,\"strCode\":\"STR_CODE_TTT\",\"wfStatus\":\"NEW\",\"version\":\"new_version\",\"planDuration\":123456,\"spentDuration\":123456,\"leftDuration\":123456}"))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test
//    void updateOneField() throws Exception {
//        mockMvc.perform(put("/rest/task/update/")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"id\":5,\"name\":\"task5_test\",\"name\":\"task8_test_updated1\"}"))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//    }
}
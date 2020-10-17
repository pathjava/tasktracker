package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void createTestObject() throws Exception {
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
    }

    @Test
    void updateOneField() throws Exception {
        mockMvc.perform(put("/rest/task/field/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 10,\n" +
                                "    \"newValue\": \"Test task 10-1\",\n" +
                                "    \"fieldName\": \"name\"\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
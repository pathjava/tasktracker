package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskUpdateFieldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskUpdateFieldController updateFieldController;

    @Test
    void updateOneField() throws Exception {
        mockMvc.perform(post("/rest/task/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 112,\n" +
                                "    \"code\": \"TT112-1\",\n" +
                                "    \"name\": \"Test task 112\",\n" +
                                "    \"description\": \"Description task 112\",\n" +
                                "    \"type\": \"BUG\",\n" +
                                "    \"project_id\": 2,\n" +
                                "    \"author\": {},\n" +
                                "    \"executor\": {},\n" +
                                "    \"created\": 1603274345,\n" +
                                "    \"updated\": null,\n" +
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

        mockMvc.perform(put("/rest/project/2/tasks/112/field")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 112,\n" +
                                "    \"newValue\": \"Test task 112-1\",\n" +
                                "    \"fieldName\": \"name\"\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test()
    void updateTask_BadRequestException_Null() {
        Exception exception = assertThrows(BadRequestException.class, () -> {
            updateFieldController.updateOneField(null, null);
        });
        assertTrue(exception.getMessage().contains("Значение обновляемого поля отсутствует!"));
    }

    @Test()
    void updateTask_BadRequestException_Wrong() {
        UpdateOneValue value = new UpdateOneValue(11L, "Test task 10-1", "name");

        Exception exception = assertThrows(BadRequestException.class, () -> {
            updateFieldController.updateOneField(10L, value);
        });
        assertTrue(exception.getMessage().contains("Данная операция недопустима!"));
    }
}
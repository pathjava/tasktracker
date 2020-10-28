package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.controller.exception.UpdateFieldNotExistException;

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
    private TaskController taskController;

    @Autowired
    private TaskUpdateFieldController updateFieldController;

    @BeforeEach
    public void createTestObject() throws Exception {
//        boolean add = taskController.addTask(
//                new Task(10L, "TT10-1", "Test task 10", "Description task 10",
//                        TaskType.BUG, TaskPriority.MAJOR, 11L, new User(), new User(),
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
    }

    @Test
    void updateOneField() throws Exception {
        mockMvc.perform(put("/rest/project/2/tasks/10/field")
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

    @Test()
    void updateTask_UpdateFieldNotExistException() {
        Exception exception = assertThrows(UpdateFieldNotExistException.class, () -> {
            updateFieldController.updateOneField(null);
        });
        assertTrue(exception.getMessage().contains("Значение обновляемого поля отсутствует!"));
    }
}
package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.service.GetListByTaskService;
import ru.progwards.tasktracker.model.WorkLog;

import java.time.ZonedDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * тестирование контроллера логов
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc
class WorkLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkLogController controller;

    @Autowired
    private GetListByTaskService<Long, WorkLog> listByTaskService;

    @Test
    void getListWorkLogs() throws Exception {
        mockMvc.perform(get("/rest/task/{task_id}/worklogs", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        Collection<WorkLog> collection = listByTaskService.getListByTaskId(2L);

        assertNotNull(collection);
    }

    @Test
    void getListWorkLogs_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.getListWorkLogs(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test
    void getListWorkLogs_NotFoundException() {
        Exception exception = assertThrows(NotFoundException.class,
                () -> controller.getListWorkLogs(120L));
        assertTrue(exception.getMessage().contains("Список логов пустой!"));
    }

    @Test
    void createWorkLog() throws Exception {
        mockMvc.perform(post("/rest/worklog/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"taskId\": 2,\n" +
                                "    \"spent\": null,\n" +
                                "    \"worker\": {},\n" +
                                "    \"when\": null,\n" +
                                "    \"description\": \"Description Log 5\",\n" +
                                "    \"estimateChange\": null,\n" +
                                "    \"estimateValue\": null\n" +
                                "}"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );
    }

    @Test
    void createWorkLog_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.createWorkLog(null));
        assertTrue(exception.getMessage().contains("Пустой объект!"));
    }

    @Test
    void deleteWorkLog() throws Exception {
        mockMvc.perform(post("/rest/worklog/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"id\": 222,\n" +
                                "    \"taskId\": 2,\n" +
                                "    \"spent\": null,\n" +
                                "    \"worker\": {},\n" +
                                "    \"when\": null,\n" +
                                "    \"description\": \"Description Log 20\",\n" +
                                "    \"estimateChange\": null,\n" +
                                "    \"estimateValue\": null\n" +
                                "}"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        mockMvc.perform(delete("/rest/worklog/{log_id}/delete", 222)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteWorkLog_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.deleteWorkLog(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test
    void deleteWorkLog_NotFoundException() {
        Exception exception = assertThrows(NotFoundException.class,
                () -> controller.deleteWorkLog(120L));
        assertTrue(exception.getMessage().contains(" не найден!"));
    }

    @Test
    void updateWorkLog() throws Exception {
        mockMvc.perform(post("/rest/worklog/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\n" +
                                "    \"taskId\": 2,\n" +
                                "    \"spent\": null,\n" +
                                "    \"worker\": {},\n" +
                                "    \"when\": null,\n" +
                                "    \"description\": \"Description Log 20\",\n" +
                                "    \"estimateChange\": null,\n" +
                                "    \"estimateValue\": null\n" +
                                "}"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        Collection<WorkLog> collection = listByTaskService.getListByTaskId(2L);
        Long id = collection.stream()
                .filter(workLog -> workLog.getDescription().equals("Description Log 20")).findFirst()
                .map(WorkLog::getId)
                .orElse(null);

        if (id != null) {
            String path = "/rest/worklog/" + id + "/update";
            mockMvc.perform(put(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                            "{\n" +
                                    "    \"id\": " + id + ",\n" +
                                    "    \"taskId\": 2,\n" +
                                    "    \"spent\": null,\n" +
                                    "    \"worker\": {},\n" +
                                    "    \"when\": null,\n" +
                                    "    \"description\": \"Description Log 20 Updated\",\n" +
                                    "    \"estimateChange\": null,\n" +
                                    "    \"estimateValue\": null\n" +
                                    "}"
                    ))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful()
                    );
        }
    }

    @Test
    void updateWorkLog_Wrong() {
        WorkLogDtoFull workLog = new WorkLogDtoFull(
                1L, 2L, null, null, ZonedDateTime.now(),
                "Description", null, null
        );

        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.updateWorkLog(2L, workLog));
        assertTrue(exception.getMessage().contains("Данная операция недопустима!"));
    }

    @Test
    void updateWorkLog_BadRequestException_Null_Object() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.updateWorkLog(anyLong(), null));
        assertTrue(exception.getMessage().contains("Пустой объект!"));
    }
}
package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.vo.WorkLog;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
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
    void getAllWorkLog() throws Exception {
        mockMvc.perform(get("/rest/log/{task_id}/listlog", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        Collection<WorkLog> collection = listByTaskService.getListByTaskId(2L);

        assertNotNull(collection);
    }

    @Test
    void getAllWorkLog_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.getAllWorkLog(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test
    void getAllWorkLog_NotFoundException() {
        Exception exception = assertThrows(NotFoundException.class,
                () -> controller.getAllWorkLog(120L));
        assertTrue(exception.getMessage().contains("Список логов пустой!"));
    }

    @Test
    void addWorkLog() throws Exception {
        mockMvc.perform(post("/rest/log/create")
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
    void addWorkLog_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.createWorkLog(null));
        assertTrue(exception.getMessage().contains("Пустой объект!"));
    }

    @Test
    void deleteWorkLog() throws Exception {
        mockMvc.perform(delete("/rest/log/{log_id}/delete", 1)
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
}
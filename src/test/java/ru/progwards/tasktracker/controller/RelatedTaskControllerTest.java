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
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * тестирование методов контроллера связанных задач
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc
class RelatedTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RelatedTaskController controller;

    @Autowired
    private GetListByTaskService<Long, RelatedTask> listByTaskService;

    @Autowired
    private GetService<Long, RelatedTask> getService;

    @Test
    void addRelatedTask() throws Exception {
        mockMvc.perform(post("/rest/relatedtask/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "  {\n" +
                                "    \"id\": 11,\n" +
                                "    \"relationTypeEntity\": {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"блокирующая\",\n" +
                                "      \"counterRelation\": {\n" +
                                "        \"id\": 2,\n" +
                                "        \"name\": \"блокируемая\"\n" +
                                "      }\n" +
                                "    },\n" +
                                "    \"parentTaskId\": 1,\n" +
                                "    \"taskId\": 2\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );
    }

    @Test
    void addRelatedTask_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.addRelatedTask(null));
        assertTrue(exception.getMessage().contains("Пустой объект!"));
    }

    @Test
    void getAllRelatedTasks() throws Exception {
        mockMvc.perform(get("/rest/relatedtask/{task_id}/list", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        Collection<RelatedTask> collection = listByTaskService.getListByTaskId(1L);

        assertNotNull(collection);
    }

    @Test
    void getAllRelatedTasks_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.getAllRelatedTasks(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test
    void getAllRelatedTasks_NotFoundException() {
        Exception exception = assertThrows(NotFoundException.class,
                () -> controller.getAllRelatedTasks(20L));
        assertTrue(exception.getMessage().contains("Список задач пустой!"));
    }

    @Test
    void deleteRelatedTask() throws Exception {
        mockMvc.perform(post("/rest/relatedtask/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "  {\n" +
                                "    \"id\": 111,\n" +
                                "    \"relationTypeEntity\": {\n" +
                                "      \"id\": 1,\n" +
                                "      \"name\": \"блокирующая\",\n" +
                                "      \"counterRelation\": {\n" +
                                "        \"id\": 2,\n" +
                                "        \"name\": \"блокируемая\"\n" +
                                "      }\n" +
                                "    },\n" +
                                "    \"parentTaskId\": 1,\n" +
                                "    \"taskId\": 2\n" +
                                "  }"
                ))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()
                );

        mockMvc.perform(delete("/rest/relatedtask/{task_id}/delete", 111)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        RelatedTask relatedTask = getService.get(111L);

        assertNull(relatedTask);
    }

    @Test
    void deleteRelatedTask_BadRequestException() {
        Exception exception = assertThrows(BadRequestException.class,
                () -> controller.deleteRelatedTask(null));
        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
    }

    @Test
    void deleteRelatedTask_NotFoundException() {
        Exception exception = assertThrows(NotFoundException.class,
                () -> controller.deleteRelatedTask(20L));
        assertTrue(exception.getMessage().contains(" не найдена!"));
    }
}
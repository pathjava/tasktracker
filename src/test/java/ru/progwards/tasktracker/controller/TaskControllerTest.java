package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskDtoFull;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.UpdateOneFieldService;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.UpdateOneValue;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тестирование методов контроллера TaskController
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private TaskController controller;
//
//    @Autowired
//    private GetListService<Task> getListService;
//
//    @Autowired
//    private GetService<String, Task> byCodeGetService;
//
//    @Qualifier("taskUpdateOneFieldService")
//    @Autowired
//    private UpdateOneFieldService<Task> oneFieldService;
//
//    @Autowired
//    private Converter<Task, TaskDtoPreview> dtoPreviewConverter;
//
//    @Autowired
//    private Converter<Task, TaskDtoFull> dtoFullConverter;
//
//    @Test
//    public void testController() {
//        assertThat(controller, is(notNullValue()));
//    }
//
//    @Test
//    void getListTasks_From_Project() throws Exception {
//        createDtoForGetListTasks();
//
//        Collection<TaskDtoPreview> collection = getListService.getList().stream()
//                .filter(task -> task.getProject_id().equals(2L))
//                .map(task -> dtoPreviewConverter.toDto(task))
//                .collect(Collectors.toList());
//
//        String jsonString = new ObjectMapper()
//                .registerModule(new JavaTimeModule()).writeValueAsString(collection);
//
//        mockMvc.perform(get("/rest/project/2/tasks"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(jsonString));
//    }
//
//    private void createDtoForGetListTasks() throws Exception {
//        mockMvc.perform(post("/rest/task/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(
//                        "{\n" +
//                                "    \"code\": \"TT110-1\",\n" +
//                                "    \"name\": \"Test task 110\",\n" +
//                                "    \"description\": \"Description task 110\",\n" +
//                                "    \"type\": \"BUG\",\n" +
//                                "    \"project_id\": 2,\n" +
//                                "    \"author\": {},\n" +
//                                "    \"executor\": {},\n" +
//                                "    \"created\": 1603274345,\n" +
//                                "    \"updated\": null,\n" +
//                                "    \"timeSpent\": null,\n" +
//                                "    \"timeLeft\": null,\n" +
//                                "    \"relatedTasks\": [],\n" +
//                                "    \"attachments\": [],\n" +
//                                "    \"workLogs\": []\n" +
//                                "  }"
//                ))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful()
//                );
//        mockMvc.perform(post("/rest/task/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(
//                        "{\n" +
//                                "    \"code\": \"TT110-2\",\n" +
//                                "    \"name\": \"Test task 110\",\n" +
//                                "    \"description\": \"Description task 110\",\n" +
//                                "    \"type\": \"BUG\",\n" +
//                                "    \"project_id\": 2,\n" +
//                                "    \"author\": {},\n" +
//                                "    \"executor\": {},\n" +
//                                "    \"created\": 1603274345,\n" +
//                                "    \"updated\": null,\n" +
//                                "    \"timeSpent\": null,\n" +
//                                "    \"timeLeft\": null,\n" +
//                                "    \"relatedTasks\": [],\n" +
//                                "    \"attachments\": [],\n" +
//                                "    \"workLogs\": []\n" +
//                                "  }"
//                ))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful()
//                );
//    }
//
//    @Test()
//    void getListTasks_From_Project_BadRequestException() {
//        Exception exception = assertThrows(BadRequestException.class,
//                () -> controller.getListTasks(null));
//        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
//    }
//
//    @Test()
//    void getListTasks_From_Project_NotFoundException() {
//        Exception exception = assertThrows(NotFoundException.class,
//                () -> controller.getListTasks(20L));
//        assertTrue(exception.getMessage().contains("Список задач пустой!"));
//    }
//
//    @Test
//    void createTask() throws Exception {
//        mockMvc.perform(post("/rest/task/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(
//                        "{\n" +
//                                "    \"code\": \"TT1110-1\",\n" +
//                                "    \"name\": \"Test task 1110\",\n" +
//                                "    \"description\": \"Description task 1110\",\n" +
//                                "    \"type\": \"BUG\",\n" +
//                                "    \"project_id\": 2,\n" +
//                                "    \"author\": {},\n" +
//                                "    \"executor\": {},\n" +
//                                "    \"created\": 1603274345,\n" +
//                                "    \"updated\": null,\n" +
//                                "    \"timeSpent\": null,\n" +
//                                "    \"timeLeft\": null,\n" +
//                                "    \"relatedTasks\": [],\n" +
//                                "    \"attachments\": [],\n" +
//                                "    \"workLogs\": []\n" +
//                                "  }"
//                ))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful()
//                );
//
//        mockMvc.perform(get("/rest/task/TT1110-1/getbycode"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name", equalTo("Test task 1110")));
//    }
//
//    @Test()
//    void createTask_BadRequestException_Null() {
//        Exception exception = assertThrows(BadRequestException.class,
//                () -> controller.createTask(null));
//        assertTrue(exception.getMessage().contains("Пустой объект!"));
//    }
//
//    @Test
//    void updateTask() throws Exception {
//        mockMvc.perform(put("/rest/task/{task_id}/update", 111)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(
//                        "{\n" +
//                                "    \"id\": 111,\n" +
//                                "    \"code\": \"TT111-1\",\n" +
//                                "    \"name\": \"Test task 111 updated\",\n" +
//                                "    \"description\": \"Description task 111\",\n" +
//                                "    \"type\": \"BUG\",\n" +
//                                "    \"project_id\": 2,\n" +
//                                "    \"author\": {},\n" +
//                                "    \"executor\": {},\n" +
//                                "    \"created\": 1603274345,\n" +
//                                "    \"updated\": null,\n" +
//                                "    \"timeSpent\": null,\n" +
//                                "    \"timeLeft\": null,\n" +
//                                "    \"relatedTasks\": [],\n" +
//                                "    \"attachments\": [],\n" +
//                                "    \"workLogs\": []\n" +
//                                "  }"
//                ))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful()
//                );
//
//        mockMvc.perform(get("/rest/task/TT111-1/getbycode"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(111)))
//                .andExpect(jsonPath("$.name", equalTo("Test task 111 updated")));
//    }
//    @Test()
//    void updateTask_BadRequestException_Null_Object() {
//        Exception exception = assertThrows(BadRequestException.class,
//                () -> controller.updateTask(anyLong(), null));
//        assertTrue(exception.getMessage().contains("Пустой объект!"));
//    }
//
//    @Test()
//    void updateTask_BadRequestException_Wrong() {
//        TaskDtoFull task = new TaskDtoFull(1L, "TT1", "Test task 1 TEST", "Description task 1",
//                null, null, 11L, null, null,
//                ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
//                null,
//                Duration.ofDays(3), Duration.ofDays(1), Duration.ofDays(2),
//                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//
//        Exception exception = assertThrows(BadRequestException.class,
//                () -> controller.updateTask(2L, task));
//        assertTrue(exception.getMessage().contains("Данная операция недопустима!"));
//    }
//
//    @Test
//    void deleteTask() throws Exception {
//        mockMvc.perform(post("/rest/task/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(
//                        "{\n" +
//                                "    \"id\": 210,\n" +
//                                "    \"code\": \"TT210-1\",\n" +
//                                "    \"name\": \"Test task 210\",\n" +
//                                "    \"description\": \"Description task 210\",\n" +
//                                "    \"type\": \"BUG\",\n" +
//                                "    \"project_id\": 2,\n" +
//                                "    \"author\": {},\n" +
//                                "    \"executor\": {},\n" +
//                                "    \"created\": 1603274345,\n" +
//                                "    \"updated\": null,\n" +
//                                "    \"timeSpent\": null,\n" +
//                                "    \"timeLeft\": null,\n" +
//                                "    \"relatedTasks\": [],\n" +
//                                "    \"attachments\": [],\n" +
//                                "    \"workLogs\": []\n" +
//                                "  }"
//                ))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful()
//                );
//
//        mockMvc.perform(delete("/rest/task/{task_id}/delete", 210)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is2xxSuccessful());
//
//        mockMvc.perform(get("/rest/project/2/tasks/210"))
//                .andExpect(status().is4xxClientError());
//    }
//
//    @Test()
//    void deleteTask_BadRequestException() {
//        Exception exception = assertThrows(BadRequestException.class,
//                () -> controller.deleteTask(null));
//        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
//    }
//
//    @Test()
//    void deleteTask_NotFoundException() {
//        Exception exception = assertThrows(NotFoundException.class,
//                () -> controller.deleteTask(20L));
//        assertTrue(exception.getMessage().contains(" не найдена!"));
//    }
//
//    @Test
//    void getByCodeTask() throws Exception {
//        mockMvc.perform(post("/rest/task/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(
//                        "{\n" +
//                                "    \"id\": 110,\n" +
//                                "    \"code\": \"TT110-1\",\n" +
//                                "    \"name\": \"Test task 110\",\n" +
//                                "    \"description\": \"Description task 110\",\n" +
//                                "    \"type\": \"BUG\",\n" +
//                                "    \"project_id\": 2,\n" +
//                                "    \"author\": {},\n" +
//                                "    \"executor\": {},\n" +
//                                "    \"created\": 1603274345,\n" +
//                                "    \"updated\": null,\n" +
//                                "    \"timeSpent\": null,\n" +
//                                "    \"timeLeft\": null,\n" +
//                                "    \"relatedTasks\": [],\n" +
//                                "    \"attachments\": [],\n" +
//                                "    \"workLogs\": []\n" +
//                                "  }"
//                ));
//
//        TaskDtoFull task = dtoFullConverter.toDto(byCodeGetService.get("TT110-1"));
//
//        String jsonString = new ObjectMapper()
//                .registerModule(new JavaTimeModule()).writeValueAsString(task);
//
//        mockMvc.perform(get("/rest/task/TT110-1/getbycode"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(jsonString));
//    }
//
//    @Test
//    void getByCodeTask_BadRequestException() {
//        Exception exception = assertThrows(BadRequestException.class,
//                () -> controller.getByCodeTask(null));
//        assertTrue(exception.getMessage().contains(" не задан или задан неверно!"));
//    }
//
//    @Test
//    void getByCodeTask_NotFoundException() {
//        Exception exception = assertThrows(NotFoundException.class,
//                () -> controller.getByCodeTask("TT10-11"));
//        assertTrue(exception.getMessage().contains(" не найдена!"));
//    }
//
//    @Test
//    void updateOneField() throws Exception {
//        mockMvc.perform(post("/rest/task/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(
//                        "{\n" +
//                                "    \"id\": 112,\n" +
//                                "    \"code\": \"TT112-1\",\n" +
//                                "    \"name\": \"Test task 112\",\n" +
//                                "    \"description\": \"Description task 112\",\n" +
//                                "    \"type\": \"BUG\",\n" +
//                                "    \"project_id\": 2,\n" +
//                                "    \"author\": {},\n" +
//                                "    \"executor\": {},\n" +
//                                "    \"created\": 1603274345,\n" +
//                                "    \"updated\": null,\n" +
//                                "    \"timeSpent\": null,\n" +
//                                "    \"timeLeft\": null,\n" +
//                                "    \"relatedTasks\": [],\n" +
//                                "    \"attachments\": [],\n" +
//                                "    \"workLogs\": []\n" +
//                                "  }"
//                ))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful()
//                );
//
//        mockMvc.perform(put("/rest/task/112/field")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(
//                        "{\n" +
//                                "    \"id\": 112,\n" +
//                                "    \"newValue\": \"Test task 112-1\",\n" +
//                                "    \"fieldName\": \"name\"\n" +
//                                "  }"
//                ))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//    }
//
//    @Test()
//    void updateOneField_BadRequestException_Null() {
//        Exception exception = assertThrows(BadRequestException.class, () -> {
//            oneFieldService.updateOneField(null);
//        });
//        assertTrue(exception.getMessage().contains("Значение обновляемого поля отсутствует!"));
//    }
//
//    @Test()
//    void updateOneField_BadRequestException_Wrong() {
//        UpdateOneValue value = new UpdateOneValue(11L, "Test task 10-1", "name");
//
//        Exception exception = assertThrows(BadRequestException.class, () -> {
//            oneFieldService.updateOneField(value);
//        });
//        assertTrue(exception.getMessage().contains("Данная операция недопустима!"));
//    }
}
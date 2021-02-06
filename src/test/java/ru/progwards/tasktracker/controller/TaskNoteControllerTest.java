package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.dto.TaskNoteDtoFull;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskNote;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.repository.TaskNoteRepository;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.progwards.tasktracker.objects.GetDtoFull.getTaskNoteDtoFull;
import static ru.progwards.tasktracker.objects.GetModel.getTaskNote;

/**
 * Тестирование методов контроллера TaskNoteController
 *
 * @author Konstantin Kishkin
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class TaskNoteControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private TaskNoteRepository taskNoteRepository;
    @Autowired
    private Converter<Task, TaskDtoPreview> taskDtoPreviewConverter;
    @Autowired
    private Converter<User, UserDtoPreview> userDtoPreviewConverter;

    private static final String GET_PATH = "/rest/task/{id}/tasknotes";
    private static final String CREATE_PATH = "/rest/tasknote/create";
    private static final String GET_LIST_BY_TASK_PATH = "/rest/task/{id}/tasknotes";
    private static final String UPDATE_PATH = "/rest/tasknote/{id}/update";
    private static final String DELETE_PATH = "/rest/tasknote/{id}/delete";
    private Task task;
    private User user;

    public static MockHttpServletRequestBuilder postJson(String uri, Object body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String json = mapper.writeValueAsString(body);
            return post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MockHttpServletRequestBuilder getUriAndMediaType(String uri) {
        return get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder getUriAndMediaType(String uri, Long id) {
        return get(uri.replace("{id}", String.valueOf(id)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder deleteUriAndMediaType(String uri, Long id) {
        return delete(uri.replace("{id}", String.valueOf(id)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder putJson(String uri, Long id, Object body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String json = mapper.writeValueAsString(body);
            return put(uri.replace("{id}", String.valueOf(id)))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void create_TaskNote() throws Exception {
        TaskNoteDtoFull dto = getDtoFull();

        MvcResult result = mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class));
        } finally {
            taskNoteRepository.deleteById(id);
        }
    }

    private TaskNoteDtoFull getDtoFull() {
        TaskNoteDtoFull dto = getTaskNoteDtoFull();
        dto.setTask(taskDtoPreviewConverter.toDto(task));
        dto.setUser(userDtoPreviewConverter.toDto(user));
        return dto;
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    @Order(2)
    void create_TaskNote_BadRequest_Validation_If_Id_is_NotNull() throws Exception {
        TaskNoteDtoFull dto = getDtoFull();

        dto.setId(1L);
        mockMvcPerformPost(dto);
    }

    private void mockMvcPerformPost(TaskNoteDtoFull dto) throws Exception {
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void getListByTask_TaskNote() throws Exception {
        TaskNote taskNote = getNote();

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_BY_TASK_PATH, taskNote.getTask().getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists());
        } finally {
            taskNoteRepository.deleteById(taskNote.getId());
        }
    }

    private TaskNote getNote() {
        TaskNote taskNote = getTaskNote();
        taskNote.setTask(task);
        taskNote.setAuthor(user);
        taskNoteRepository.save(taskNote);
        return taskNote;
    }

    @Test
    void getListByTask_TaskNote_Validation_when_Id_is_negative() throws Exception {
        mockMvcPerformGet(GET_LIST_BY_TASK_PATH, -1L);
    }

    private void mockMvcPerformGet(String getPath, long l) throws Exception {
        mockMvc.perform(
                getUriAndMediaType(getPath, l))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void getListByTask_TaskNote_when_return_Empty_List() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_LIST_BY_TASK_PATH, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void update_TaskNote() throws Exception {
        TaskNote taskNote = getNote();
        TaskNoteDtoFull dto = getDtoFull();
        dto.setComment("updated comment");
        dto.setId(taskNote.getId());

        MvcResult result = mockMvc.perform(
                putJson(UPDATE_PATH, taskNote.getId(), dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.description", equalTo("updated comment")));
        } finally {
            taskNoteRepository.deleteById(id);
        }
    }

    @Test
    void update_TaskNote_when_NotFound() throws Exception {
        TaskNoteDtoFull dto = getDtoFull();
        dto.setId(Long.MAX_VALUE);

        mockMvc.perform(
                putJson(UPDATE_PATH, Long.MAX_VALUE, dto))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void delete_TaskNote() {
        TaskNote taskNote = getNote();

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, taskNote.getId()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            taskNoteRepository.deleteById(taskNote.getId());
        }
    }

    @Test
    void delete_TaskNote_Validation_when_Id_is_negative() throws Exception {
        mockMvcPerformDelete(-1L);
    }

    private void mockMvcPerformDelete(long l) throws Exception {
        mockMvc.perform(
                deleteUriAndMediaType(DELETE_PATH, l))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }
}

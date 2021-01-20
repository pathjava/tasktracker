package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
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
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.*;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.progwards.tasktracker.objects.GetDtoFull.getWorkLogDtoFull;
import static ru.progwards.tasktracker.objects.GetModel.*;


/**
 * Тестирование методов контроллера WorkLogController
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestPropertySource(locations = "classpath:application-dev.properties")
@ActiveProfiles("dev")
class WorkLogControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WorkLogRepository workLogRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private Converter<Task, TaskDtoPreview> taskDtoPreviewConverter;
    @Autowired
    private Converter<User, UserDtoPreview> userDtoPreviewConverter;

    private static final String GET_PATH = "/rest/worklog/{id}";
    private static final String GET_LIST_PATH = "/rest/worklog/list";
    private static final String GET_LIST_BY_TASK_PATH = "/rest/task/{id}/worklogs";
    private static final String CREATE_PATH = "/rest/worklog/create";
    private static final String DELETE_PATH = "/rest/worklog/{id}/delete";
    private static final String UPDATE_PATH = "/rest/worklog/{id}/update";
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

    @BeforeEach
    public void creator() {
        user = getUser();
        userRepository.save(user);

        Project project = getProject();
        project.setOwner(user);
        projectRepository.save(project);

        TaskType taskType = getTaskType();
        taskTypeRepository.save(taskType);

        task = getTask();
        task.setAuthor(user);
        task.setProject(project);
        task.setType(taskType);
        taskRepository.save(task);
    }

    @Test
    @Order(1)
    void create_WorkLog() throws Exception {
        WorkLogDtoFull dto = getDtoFull();

        MvcResult result = mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.description", equalTo("Description workLog")));
        } finally {
            workLogRepository.deleteById(id);
        }
    }

    private WorkLogDtoFull getDtoFull() {
        WorkLogDtoFull dto = getWorkLogDtoFull();
        dto.setTask(taskDtoPreviewConverter.toDto(task));
        dto.setWorker(userDtoPreviewConverter.toDto(user));
        return dto;
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    @Order(2)
    void create_WorkLog_BadRequest_Validation_If_Id_is_NotNull() throws Exception {
        WorkLogDtoFull dto = getDtoFull();

        dto.setId(1L);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(3)
    void create_WorkLog_BadRequest_Validation_If_Task_Null() throws Exception {
        WorkLogDtoFull dto = getDtoFull();

        dto.setTask(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(4)
    void create_WorkLog_BadRequest_Validation_If_Spent_Null() throws Exception {
        WorkLogDtoFull dto = getDtoFull();

        dto.setSpent(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(5)
    void create_WorkLog_BadRequest_Validation_If_Worker_Null() throws Exception {
        WorkLogDtoFull dto = getDtoFull();

        dto.setWorker(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(6)
    void create_WorkLog_BadRequest_Validation_If_Start_Null() throws Exception {
        WorkLogDtoFull dto = getDtoFull();

        dto.setStart(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(7)
    void create_WorkLog_BadRequest_Validation_If_Description_Null() throws Exception {
        WorkLogDtoFull dto = getDtoFull();

        dto.setDescription(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(8)
    void create_WorkLog_BadRequest_Validation_If_EstimateChange_Null() throws Exception {
        WorkLogDtoFull dto = getDtoFull();

        dto.setEstimateChange(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void get_WorkLog() throws Exception {
        WorkLog wl = getLog();

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_PATH, wl.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(wl.getId()), Long.class));
        } finally {
            workLogRepository.deleteById(wl.getId());
        }
    }

    private WorkLog getLog() {
        WorkLog wl = getWorkLog();
        wl.setTask(task);
        wl.setWorker(user);
        workLogRepository.save(wl);
        return wl;
    }

    @Test
    void get_WorkLog_when_NotFound() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void get_WorkLog_Validation_when_Id_is_negative() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, -1L))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void get_WorkLog_Validation_when_Id_more_value_Long() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, Long.MAX_VALUE + 1))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void getListByTask_WorkLog() throws Exception {
        WorkLog wl = getLog();

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_BY_TASK_PATH, wl.getTask().getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists());
        } finally {
            workLogRepository.deleteById(wl.getId());
        }
    }

    @Test
    void getListByTask_WorkLog_Validation_when_Id_is_negative() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_LIST_BY_TASK_PATH, -1L))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void getListByTask_WorkLog_Validation_when_Id_more_value_Long() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_LIST_BY_TASK_PATH, Long.MAX_VALUE + 1))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void getListByTask_WorkLog_when_return_Empty_List() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_LIST_BY_TASK_PATH, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void getList_WorkLog() throws Exception {
        WorkLog wl = getLog();
        List<WorkLog> listLog = List.of(wl);
        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_PATH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists());
        } finally {
            workLogRepository.deleteAll(listLog);
        }
    }

//    @Test
//    void getList_WorkLog_when_return_Empty_List() throws Exception {
//        mockMvc.perform(
//                getUriAndMediaType(GET_LIST_PATH))
//                .andExpect(status().isNotFound())
//                .andExpect(mvcResult ->
//                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
//    }

    @Test
    void update_WorkLog() throws Exception {
        WorkLog wl = getLog();
        WorkLogDtoFull dto = getDtoFull();
        dto.setDescription("updated description");
        dto.setId(wl.getId());

        MvcResult result = mockMvc.perform(
                putJson(UPDATE_PATH, wl.getId(), dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.description", equalTo("updated description")));
        } finally {
            workLogRepository.deleteById(id);
        }
    }

    @Test
    void update_WorkLog_when_Request_Id_is_different_Dto_Id() throws Exception {
        WorkLog wl = getLog();
        WorkLogDtoFull dto = getDtoFull();
        dto.setId(wl.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, wl.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof BadRequestException));
        } finally {
            workLogRepository.deleteById(wl.getId());
        }
    }

    @Test
    void update_WorkLog_when_NotFound() throws Exception {
        WorkLogDtoFull dto = getDtoFull();
        dto.setId(Long.MAX_VALUE);

        mockMvc.perform(
                putJson(UPDATE_PATH, Long.MAX_VALUE, dto))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void delete_WorkLog() {
        WorkLog wl = getLog();

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, wl.getId()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            workLogRepository.deleteById(wl.getId());
        }
    }

    @Test
    void delete_WorkLog_Validation_when_Id_is_negative() throws Exception {
        mockMvc.perform(
                deleteUriAndMediaType(DELETE_PATH, -1L))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void delete_WorkLog_Validation_when_Id_more_value_Long() throws Exception {
        mockMvc.perform(
                deleteUriAndMediaType(DELETE_PATH, Long.MAX_VALUE + 1))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

}
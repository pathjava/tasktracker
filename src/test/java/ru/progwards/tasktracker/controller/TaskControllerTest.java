package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.progwards.tasktracker.dto.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.*;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.progwards.tasktracker.objects.GetDtoFull.getTaskDtoFull;
import static ru.progwards.tasktracker.objects.GetModel.*;
import static ru.progwards.tasktracker.objects.GetModel.getProjectModel;

/**
 * Тестирование методов контроллера TaskController
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private Converter<TaskType, TaskTypeDtoPreview> taskTypeDtoPreviewConverter;
    @Autowired
    private Converter<Task, TaskDtoFull> taskDtoFullConverter;
    @Autowired
    private Converter<Project, ProjectDtoPreview> projectDtoPreviewConverter;
    @Autowired
    private Converter<User, UserDtoPreview> userDtoPreviewConverter;

    private static final String GET_PATH = "/rest/task/{id}";
    private static final String GET_BY_CODE_PATH = "/rest/task/{code}/getbycode";
    private static final String GET_LIST_PATH = "/rest/task/list";
    private static final String GET_LIST_BY_PROJECT_PATH = "/rest/project/{id}/tasks";
    private static final String CREATE_PATH = "/rest/task/create";
    private static final String DELETE_PATH = "/rest/task/{id}/delete";
    private static final String UPDATE_PATH = "/rest/task/{id}/update";
    private static final String UPDATE_FIELD_PATH = "/rest/task/{id}/updatefield";

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

    public static MockHttpServletRequestBuilder getUriAndMediaType(String uri, String code) {
        return get(uri.replace("{code}", code))
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

    private Task getTask() {
        User user = userCreator();
        Project project = projectCreator(user);
        TaskType taskType = taskTypeCreator();

        Task task = getTaskModel();
        task.setAuthor(user);
        task.setProject(project);
        task.setType(taskType);
        return taskRepository.save(task);
    }

    private TaskDtoFull getTaskDto() {
        User user = userCreator();
        Project project = projectCreator(user);
        TaskType taskType = taskTypeCreator();

        TaskDtoFull dto = getTaskDtoFull();
        dto.setAuthor(userDtoPreviewConverter.toDto(user));
        dto.setProject(projectDtoPreviewConverter.toDto(project));
        dto.setType(taskTypeDtoPreviewConverter.toDto(taskType));
        return dto;
    }

    private TaskType taskTypeCreator() {
        TaskType taskType = getTaskTypeModel();
        taskTypeRepository.save(taskType);
        return taskType;
    }

    private Project projectCreator(User user) {
        Project project = getProjectModel();
        project.setOwner(user);
        projectRepository.save(project);
        return project;
    }

    private User userCreator() {
        User user = getUserModel();
        userRepository.save(user);
        return user;
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    void create_Task() throws Exception {
        TaskDtoFull dto = getTaskDto();

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
            taskRepository.deleteById(id);
        }
    }

    @Test
    void create_Task_Validation_If_Id_is_NotNull() throws Exception {
        TaskDtoFull dto = getTaskDto();

        dto.setId(anyLong());
        mockMvcPerformPost(dto);
    }

    private void mockMvcPerformPost(TaskDtoFull dto) throws Exception {
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void create_Task_Validation_If_Code_is_NotNull() throws Exception {
        TaskDtoFull dto = getTaskDto();

        dto.setCode(anyString());
        mockMvcPerformPost(dto);
    }

    @Test
    void create_Task_Validation_If_Name_is_Blank() throws Exception {
        TaskDtoFull dto = getTaskDto();

        dto.setName("   ");
        mockMvcPerformPost(dto);
    }

    @Test
    void create_Task_Validation_If_TaskType_is_Null() throws Exception {
        TaskDtoFull dto = getTaskDto();

        dto.setType(null);
        mockMvcPerformPost(dto);
    }

    @Test
    void create_Task_Validation_If_Project_is_Null() throws Exception {
        TaskDtoFull dto = getTaskDto();

        dto.setProject(null);
        mockMvcPerformPost(dto);
    }

    @Test
    void create_Task_Validation_If_Author_is_Null() throws Exception {
        TaskDtoFull dto = getTaskDto();

        dto.setAuthor(null);
        mockMvcPerformPost(dto);
    }

    @Test
    void create_Task_Validation_If_Created_is_NotNull() throws Exception {
        TaskDtoFull dto = getTaskDto();

        dto.setCreated(ZonedDateTime.now());
        mockMvcPerformPost(dto);
    }

    @Test
    void create_Task_Validation_If_Updated_is_NotNull() throws Exception {
        TaskDtoFull dto = getTaskDto();

        dto.setUpdated(ZonedDateTime.now());
        mockMvcPerformPost(dto);
    }

    @Test
    void get_Task() throws Exception {
        Task task = getTask();

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_PATH, task.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(task.getId()), Long.class));
        } finally {
            taskRepository.deleteById(task.getId());
        }
    }

    @Test
    void get_Task_when_NotFound() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void get_Task_Validation_when_Id_is_negative() throws Exception {
        mockMvcPerformGet(GET_PATH, -1L);
    }

    private void mockMvcPerformGet(String getPath, long l) throws Exception {
        mockMvc.perform(
                getUriAndMediaType(getPath, l))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void getByCode_Task() throws Exception {
        Task task = getTask();

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_BY_CODE_PATH, task.getCode()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code", is(task.getCode()), String.class));
        } finally {
            taskRepository.deleteById(task.getId());
        }

    }

    @Test
    void getByCode_Task_when_NotFound() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_BY_CODE_PATH, "anyString()"))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void getListByProject_Task() throws Exception {
        Task task = getTask();

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_BY_PROJECT_PATH, task.getProject().getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists());
        } finally {
            taskRepository.deleteById(task.getId());
        }
    }

    @Test
    void getListByProject_Task_Validation_when_Id_is_negative() throws Exception {
        mockMvcPerformGet(GET_LIST_BY_PROJECT_PATH, -1L);
    }

    @Test
    void getListByProject_Task_when_return_Empty_List() throws Exception {
        Project project = projectCreator(userCreator());

        mockMvc.perform(
                getUriAndMediaType(GET_LIST_BY_PROJECT_PATH, project.getId()))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void getList_Task() throws Exception {
        Task task = getTask();
        List<Task> list = List.of(task);
        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_PATH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists());
        } finally {
            taskRepository.deleteAll(list);
        }
    }

//    @Test
//    void getList_Task_when_return_Empty_List() {
//    }

    @Test
    void update_Task() throws Exception {
        Task task = getTask();
        TaskDtoFull dto = taskDtoFullConverter.toDto(task);
        dto.setName("updated Task name");

        MvcResult result = mockMvc.perform(
                putJson(UPDATE_PATH, task.getId(), dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.name", equalTo("updated Task name")));
        } finally {
            taskRepository.deleteById(id);
        }
    }

    @Test
    void update_Task_when_Request_Id_is_different_Dto_Id() throws Exception {
        Task task = getTask();
        TaskDtoFull dto = taskDtoFullConverter.toDto(task);
        dto.setId(task.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, task.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof BadRequestException));
        } finally {
            taskRepository.deleteById(task.getId());
        }
    }

    @Test
    void update_Task_when_NotFound() throws Exception {
        Task task = getTask();
        TaskDtoFull dto = taskDtoFullConverter.toDto(task);
        dto.setId(Long.MAX_VALUE);

        mockMvc.perform(
                putJson(UPDATE_PATH, Long.MAX_VALUE, dto))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void delete_Task() throws Exception {
        Task task = getTask();

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, task.getId()))
                    .andExpect(status().isOk())
                    .andExpect(mvcResult ->
                            assertTrue(Objects.requireNonNull(taskRepository.findById(task.getId())
                                    .orElse(null)).isDeleted()));
        } finally {
            taskRepository.deleteById(task.getId());
        }
    }

    @Test
    void delete_Task_Validation_when_Id_is_negative() throws Exception {
        mockMvcPerformDelete(-1L);
    }

    private void mockMvcPerformDelete(long l) throws Exception {
        mockMvc.perform(
                deleteUriAndMediaType(DELETE_PATH, l))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void updateOneField_Task() throws Exception {
        Task task = getTask();
        UpdateOneValue updateOneValue = new UpdateOneValue(
                task.getId(),
                "Update Task name",
                "name"
        );

        MvcResult result = mockMvc.perform(
                putJson(UPDATE_FIELD_PATH, task.getId(), updateOneValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.name", equalTo("Update Task name")));
        } finally {
            taskRepository.deleteById(id);
        }
    }

    @Test
    void updateOneField_Task_when_Request_Id_is_different_Dto_Id() throws Exception {
        Task task = getTask();
        UpdateOneValue updateOneValue = new UpdateOneValue(
                task.getId() + 1,
                "Update Task name",
                "name"
        );

        try {
            mockMvc.perform(
                    putJson(UPDATE_FIELD_PATH, task.getId(), updateOneValue))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof BadRequestException));
        } finally {
            taskRepository.deleteById(task.getId());
        }
    }

    @Test
    void updateOneField_when_NotFound() throws Exception {
        UpdateOneValue updateOneValue = new UpdateOneValue(
                Long.MAX_VALUE,
                "Update Task name",
                "name"
        );

        mockMvc.perform(
                putJson(UPDATE_FIELD_PATH, Long.MAX_VALUE, updateOneValue))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }
}
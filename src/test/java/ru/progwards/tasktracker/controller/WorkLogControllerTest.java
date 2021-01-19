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
import ru.progwards.tasktracker.dto.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.progwards.tasktracker.controller.objects.GetDto.getWorkLogDto;
import static ru.progwards.tasktracker.controller.objects.GetModel.*;


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
    private static final String GET_LIST_BY_TASK_PATH = "/rest/worklog/{id}/worklogs";
    private static final String CREATE_PATH = "/rest/worklog/create";
    private static final String DELETE_PATH = "/rest/worklog/{id}/delete";
    private static final String UPDATE_PATH = "/rest/worklog/{id}/update";
    private Task task;
    private User user;
    private Project project;
    private TaskType taskType;

    public static MockHttpServletRequestBuilder postJson(String uri, Object body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
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
            String json = new ObjectMapper().writeValueAsString(body);
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

        project = getProject();
        project.setOwner(user);
        projectRepository.save(project);

        taskType = getTaskType();
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
        WorkLogDtoFull dto = getWorkLogDto();
        dto.setTask(taskDtoPreviewConverter.toDto(task));
        dto.setWorker(userDtoPreviewConverter.toDto(user));

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

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    void get_WorkLog() {
    }

    @Test
    void getListByTask_WorkLog() {
    }

    @Test
    void getList_WorkLog() {
    }

    @Test
    void update_WorkLog() {
    }

    @Test
    void delete_WorkLog() {
    }

}
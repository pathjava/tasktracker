package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
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
import ru.progwards.tasktracker.dto.RelatedTaskDtoFull;
import ru.progwards.tasktracker.dto.RelationTypeDtoPreview;
import ru.progwards.tasktracker.dto.TaskDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.*;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.progwards.tasktracker.objects.GetDtoFull.getRelatedTaskDtoFull;
import static ru.progwards.tasktracker.objects.GetModel.*;

/**
 * тестирование методов контроллера связанных задач
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("dev")
class RelatedTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RelatedTaskRepository relatedTaskRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private RelationTypeRepository relationTypeRepository;
    @Autowired
    private Converter<Task, TaskDtoPreview> taskDtoPreviewConverter;
    @Autowired
    private Converter<RelatedTask, RelatedTaskDtoFull> relatedTaskDtoFullConverter;
    @Autowired
    private Converter<RelationType, RelationTypeDtoPreview> relationTypeDtoPreviewConverter;

    private static final String GET_PATH = "/rest/relatedtask/{id}";
    private static final String GET_LIST_PATH = "/rest/relatedtask/list";
    private static final String GET_LIST_BY_TASK_PATH = "/rest/relatedtask/{id}/list";
    private static final String CREATE_PATH = "/rest/relatedtask/create";
    private static final String DELETE_PATH = "/rest/relatedtask/{id}/delete";

    public static MockHttpServletRequestBuilder postJson(String uri, Object body) {
        try {
            String json = new ObjectMapper().writeValueAsString(body);
            return post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MockHttpServletRequestBuilder getUriAndMediaType(String uri, Long id) {
        return get(uri.replace("{id}", String.valueOf(id)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder getUriAndMediaType(String uri) {
        return get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public static MockHttpServletRequestBuilder deleteUriAndMediaType(String uri, Long id) {
        return delete(uri.replace("{id}", String.valueOf(id)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
    }

    public TaskDtoPreview taskCreator() {
        User user = getUserModel();
        userRepository.save(user);

        Project project = getProjectModel();
        project.setOwner(user);
        projectRepository.save(project);

        TaskType taskType = getTaskTypeModel();
        taskTypeRepository.save(taskType);

        Task task = getTaskModel();
        task.setAuthor(user);
        task.setProject(project);
        task.setType(taskType);
        return taskDtoPreviewConverter.toDto(taskRepository.save(task));
    }

    public RelationTypeDtoPreview typeCreator() {
        RelationType type = getRelationTypeModel();
        type.setName("type name");
        return relationTypeDtoPreviewConverter.toDto(relationTypeRepository.save(type));
    }

    @Test
    void create_RelatedTask() throws Exception {
        RelatedTaskDtoFull dto = getDtoFull();
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
            Long typeId = dto.getRelationType().getId();
            relatedTaskRepository.deleteById(id);
            relationTypeRepository.deleteById(typeId);
        }
    }

    private RelatedTaskDtoFull getDtoFull() {
        RelatedTaskDtoFull dto = getRelatedTaskDtoFull();
        dto.setRelationType(typeCreator());
        dto.setCurrentTaskId(taskCreator().getId());
        dto.setAttachedTask(taskCreator());
        return dto;
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    void create_RelatedTask_BadRequest_Validation_If_Id_is_NotNull() throws Exception {
        RelatedTaskDtoFull dto = getDtoFull();
        dto.setId(1L);
        try {
            mockMvcPerformPost(dto);
        } finally {
            Long typeId = dto.getRelationType().getId();
            relationTypeRepository.deleteById(typeId);
        }
    }

    private void mockMvcPerformPost(RelatedTaskDtoFull dto) throws Exception {
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void create_RelatedTask_BadRequest_Validation_If_RelationType_is_Null() throws Exception {
        RelatedTaskDtoFull dto = getDtoFull();
        Long typeId = dto.getRelationType().getId();
        dto.setRelationType(null);
        try {
            mockMvcPerformPost(dto);
        } finally {
            relationTypeRepository.deleteById(typeId);
        }
    }

    @Test
    void create_RelatedTask_BadRequest_Validation_If_Task_is_Null() throws Exception {
        RelatedTaskDtoFull dto = getDtoFull();
        Long typeId = dto.getRelationType().getId();
        dto.setCurrentTaskId(null);
        try {
            mockMvcPerformPost(dto);
        } finally {
            relationTypeRepository.deleteById(typeId);
        }
    }

    @Test
    void create_RelatedTask_BadRequest_Validation_If_AttachedTask_is_Null() throws Exception {
        RelatedTaskDtoFull dto = getDtoFull();
        Long typeId = dto.getRelationType().getId();
        dto.setAttachedTask(null);
        try {
            mockMvcPerformPost(dto);
        } finally {
            relationTypeRepository.deleteById(typeId);
        }
    }

    @Test
    void getListByTask_RelatedTask() throws Exception {
        RelatedTask relatedTask = relatedTaskRepository.save(relatedTaskDtoFullConverter.toModel(getDtoFull()));
        Long typeId = relatedTask.getRelationType().getId();
        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_BY_TASK_PATH, relatedTask.getCurrentTask().getId()))
                    .andExpect(status().isOk());
        } finally {
            relatedTaskRepository.deleteById(relatedTask.getId());
            relationTypeRepository.deleteById(typeId);
        }
    }

    @Test
    void getListByTask_RelatedTask_Validation_when_Id_is_negative() throws Exception {
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
    void getListByTask_RelatedTask_when_return_Empty_List() throws Exception {
        TaskDtoPreview taskDtoPreview = taskCreator();

        mockMvc.perform(
                getUriAndMediaType(GET_LIST_BY_TASK_PATH, taskDtoPreview.getId()))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void get_RelatedTask() throws Exception {
        RelatedTask relatedTask = relatedTaskRepository.save(relatedTaskDtoFullConverter.toModel(getDtoFull()));
        Long typeId = relatedTask.getRelationType().getId();

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_PATH, relatedTask.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(relatedTask.getId()), Long.class));
        } finally {
            relatedTaskRepository.deleteById(relatedTask.getId());
            relationTypeRepository.deleteById(typeId);
        }
    }

    @Test
    void get_RelatedTask_when_NotFound() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void get_RelatedTask_Validation_when_Id_is_negative() throws Exception {
        mockMvcPerformGet(GET_PATH, -1L);
    }

    @Test
    void getList_RelatedTask() throws Exception {
        RelatedTask relatedTask = relatedTaskRepository.save(relatedTaskDtoFullConverter.toModel(getDtoFull()));
        Long typeId = relatedTask.getRelationType().getId();

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_PATH))
                    .andExpect(status().isOk());
        } finally {
            relatedTaskRepository.deleteById(relatedTask.getId());
            relationTypeRepository.deleteById(typeId);
        }
    }

    @Test
    void delete_RelatedTask() throws Exception {
        RelatedTask relatedTask = relatedTaskRepository.save(relatedTaskDtoFullConverter.toModel(getDtoFull()));
        Long typeId = relatedTask.getRelationType().getId();

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, relatedTask.getId()))
                    .andExpect(status().isOk())
                    .andExpect(mvcResult ->
                            assertTrue(Objects.requireNonNull(relatedTaskRepository.findById(relatedTask.getId())
                                    .orElse(null)).isDeleted()));
        } finally {
            relatedTaskRepository.deleteById(relatedTask.getId());
            relationTypeRepository.deleteById(typeId);
        }
    }

    @Test
    void delete_RelatedTask_Validation_when_Id_is_negative() throws Exception {
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
package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
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
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.repository.TaskTypeRepository;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.progwards.tasktracker.objects.GetDtoFull.getTaskTypeDtoFull;
import static ru.progwards.tasktracker.objects.GetModel.getProjectModel;
import static ru.progwards.tasktracker.objects.GetModel.getTaskTypeModel;

/**
 * Тестирование методов контроллера TaskTypeController
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TaskTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private static final String GET_PATH = "/rest/tasktype/{id}";
    private static final String GET_LIST_PATH = "/rest/tasktype/list";
    private static final String GET_LIST_BY_PROJECT_PATH = "/rest/tasktype/{id}/list";
    private static final String CREATE_PATH = "/rest/tasktype/create";
    private static final String DELETE_PATH = "/rest/tasktype/{id}/delete";
    private static final String UPDATE_PATH = "/rest/tasktype/{id}/update";

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

    @Test
    void create_TaskType() throws Exception {
        MvcResult result = mockMvc.perform(
                postJson(CREATE_PATH, getTaskTypeDtoFull()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class));
        } finally {
            taskTypeRepository.deleteById(id);
        }
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    void create_TaskType_BadRequest_Validation_If_Id_is_NotNull() throws Exception {
        TaskTypeDtoFull dto = getTaskTypeDtoFull();
        dto.setId(anyLong());
        mockMvcPerformPost(dto);
    }

    private void mockMvcPerformPost(TaskTypeDtoFull dto) throws Exception {
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void create_TaskType_BadRequest_Validation_If_Name_is_Empty() throws Exception {
        TaskTypeDtoFull dto = getTaskTypeDtoFull();
        dto.setName("   ");
        mockMvcPerformPost(dto);
    }

    @Test
    void create_TaskType_BadRequest_Validation_If_Name_is_Null() throws Exception {
        TaskTypeDtoFull dto = getTaskTypeDtoFull();
        dto.setName(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isInternalServerError())
                .andExpect(mvcResult -> assertNotNull(mvcResult.getResolvedException()));
    }

    @Test
    void get_TaskType() throws Exception {
        TaskType tt = taskTypeRepository.save(getTaskTypeModel());

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_PATH, tt.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(tt.getId()), Long.class));
        } finally {
            taskTypeRepository.deleteById(tt.getId());
        }
    }

    @Test
    void get_TaskType_when_NotFound() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void get_TaskType_Validation_when_Id_is_negative() throws Exception {
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
    void getList_TaskType() throws Exception {
        TaskType one = getTaskTypeModel();
        one.setName("name one");
        TaskType two = getTaskTypeModel();
        two.setName("name two");
        List<TaskType> listType = List.of(one, two);
        taskTypeRepository.saveAll(listType);

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_PATH))
                    .andExpect(status().isOk());
        } finally {
            taskTypeRepository.deleteAll(listType);
        }
    }

//    @Test
//    void getList_TaskType_when_return_Empty_List() throws Exception {
//        mockMvc.perform(
//                getUriAndMediaType(GET_LIST_PATH))
//                .andExpect(status().isNotFound())
//                .andExpect(mvcResult ->
//                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
//    }

    @Test
    void getListByProject_TaskType() throws Exception {
        Project project = getProjectModel();
        projectRepository.save(project);

        TaskType one = getTaskTypeModel();
        one.setName("name one");
        one.setProject(project);

        TaskType two = getTaskTypeModel();
        two.setName("name two");
        two.setProject(project);

        List<TaskType> listType = List.of(one, two);
        taskTypeRepository.saveAll(listType);

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_BY_PROJECT_PATH, project.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].name", containsInAnyOrder("name one", "name two")));
        } finally {
            taskTypeRepository.deleteAll(listType);
            projectRepository.deleteById(project.getId());
        }
    }

    @Test
    void getListByProject_TaskType_Validation_when_Id_is_negative() throws Exception {
        mockMvcPerformGet(GET_LIST_BY_PROJECT_PATH, -1L);
    }

    @Test
    void getListByProject_TaskType_when_return_Empty_List() throws Exception {
        Project project = getProjectModel();
        projectRepository.save(project);

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_BY_PROJECT_PATH, project.getId()))
                    .andExpect(status().isNotFound())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
        } finally {
            projectRepository.deleteById(project.getId());
        }
    }

    @Test
    void delete_TaskType() {
        TaskType tt = taskTypeRepository.save(getTaskTypeModel());

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, tt.getId()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            taskTypeRepository.deleteById(tt.getId());
        }
    }

    @Test
    void delete_TaskType_Validation_when_Id_is_negative() throws Exception {
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
    void update_TaskType() throws Exception {
        TaskType tt = taskTypeRepository.save(getTaskTypeModel());
        TaskTypeDtoFull dto = getTaskTypeDtoFull();
        dto.setName("updated name");
        dto.setId(tt.getId());

        MvcResult result = mockMvc.perform(
                putJson(UPDATE_PATH, tt.getId(), dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.name", equalTo("updated name")));
        } finally {
            taskTypeRepository.deleteById(id);
        }
    }

    @Test
    void update_TaskType_when_Request_Id_is_different_Dto_Id() throws Exception {
        TaskType tt = taskTypeRepository.save(getTaskTypeModel());
        TaskTypeDtoFull dto = getTaskTypeDtoFull();
        dto.setName("another name");
        dto.setId(tt.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, tt.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof BadRequestException));
        } finally {
            taskTypeRepository.deleteById(tt.getId());
        }
    }

    @Test
    void update_TaskType_when_Name_is_already_used_another_TaskType() throws Exception {
        TaskType tt = taskTypeRepository.save(getTaskTypeModel());
        TaskTypeDtoFull dto = getTaskTypeDtoFull();
        dto.setId(tt.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, tt.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult -> assertNotNull(mvcResult.getResolvedException()));
        } finally {
            taskTypeRepository.deleteById(tt.getId());
        }
    }

    @Test
    void update_TaskType_when_NotFound() throws Exception {
        TaskTypeDtoFull dto = getTaskTypeDtoFull();
        dto.setId(Long.MAX_VALUE);

        mockMvc.perform(
                putJson(UPDATE_PATH, Long.MAX_VALUE, dto))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }
}
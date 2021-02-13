package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.progwards.tasktracker.dto.TaskTypeDtoPreview;
import ru.progwards.tasktracker.dto.WorkFlowActionDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.repository.WorkFlowActionRepository;
import ru.progwards.tasktracker.repository.WorkFlowStatusRepository;

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
import static ru.progwards.tasktracker.objects.GetDtoFull.getWorkFlowActionDtoFull;
import static ru.progwards.tasktracker.objects.GetModel.*;

/**
 * Тестирование методов контроллера WorkFlowActionController
 * @author Aleksandr Sidelnikov
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class WorkFlowActionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Converter<TaskType, TaskTypeDtoPreview> workFlowStatusDtoPreviewConverter;
    @Autowired
    private WorkFlowActionRepository workFlowActionRepository;
    @Autowired
    private WorkFlowStatusRepository workFlowStatusRepository;

    private static final String GET_PATH = "/rest/workflowaction/{id}";
    private static final String GET_LIST_PATH = "/rest/workflowaction/list";
    private static final String CREATE_PATH = "/rest/workflowaction/create";
    private static final String DELETE_PATH = "/rest/workflowaction/{id}/delete";
    private static final String UPDATE_PATH = "/rest/workflowaction/{id}/update";

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
    private WorkFlowAction getWorkFlowAction() {
        WorkFlowStatus workFlowStatus = workFlowStatusCreator();

        WorkFlowAction workFlowAction = getWorkFlowActionModel();
        workFlowAction.setParentStatus(workFlowStatus);
        workFlowAction.setNextStatus(workFlowStatus);
        return workFlowActionRepository.save(workFlowAction);
    }

    private WorkFlowStatus workFlowStatusCreator() {
        WorkFlowStatus workFlowStatus = getWorkFlowStatusModel();
        workFlowStatusRepository.save(workFlowStatus);
        return workFlowStatus;
    }

    private WorkFlowActionDtoFull getWorkFlowActionDto() {
        WorkFlowActionDtoFull dto = getWorkFlowActionDtoFull();
        dto.setParentStatus_id(workFlowStatusCreator().getId());
        dto.setNextStatus_id(workFlowStatusCreator().getId());
        return dto;
    }

    @Test
    @Order(1)
    void create_WorkFlowAction() throws Exception {
        WorkFlowActionDtoFull dto = getWorkFlowActionDto();
        //  getWorkFlowActionDtoFull()
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
            workFlowActionRepository.deleteById(id);
        }
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    @Order(2)
    void create_WorkFlowAction_BadRequest_Validation_If_Id_is_NotNull() throws Exception {
        WorkFlowActionDtoFull dto = getWorkFlowActionDtoFull();
        dto.setId(1L);
        mockMvcPerformPost(dto);
    }

    private void mockMvcPerformPost(WorkFlowActionDtoFull dto) throws Exception {
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(3)
    void create_WorkFlowAction_BadRequest_Validation_If_Name_is_Empty() throws Exception {
        WorkFlowActionDtoFull dto = getWorkFlowActionDtoFull();
        dto.setName("");
        mockMvcPerformPost(dto);
    }

    @Test
    @Order(4)
    void create_WorkFlowAction_BadRequest_Validation_If_Name_is_Null() throws Exception {
        WorkFlowActionDtoFull dto = getWorkFlowActionDtoFull();
        dto.setName(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isInternalServerError())
                .andExpect(mvcResult -> assertNotNull(mvcResult.getResolvedException()));
    }

    @Test
    @Order(5)
    void get_WorkFlowAction() throws Exception {
        WorkFlowAction workFlowAction = getWorkFlowAction();
        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_PATH, workFlowAction.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(workFlowAction.getId()), Long.class));
        } finally {
            workFlowActionRepository.deleteById(workFlowAction.getId());
        }
    }

    @Test
    @Order(6)
    void get_WorkFlowAction_when_NotFound() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(7)
    void get_WorkFlowAction_Validation_when_Id_is_negative() throws Exception {
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
    @Order(9)
    void getList_WorkFlowAction() throws Exception {
        WorkFlowAction one = getWorkFlowAction();
        List<WorkFlowAction> listType = List.of(one);

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_LIST_PATH))
                    .andExpect(status().isOk());
        } finally {
            workFlowActionRepository.deleteAll(listType);
        }
    }

    @Test
    @Order(15)
    void delete_WorkFlowAction() {
        WorkFlowAction tt = workFlowActionRepository.save(getWorkFlowActionModel());

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, tt.getId()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            workFlowActionRepository.deleteById(tt.getId());
        }
    }

    @Test
    @Order(16)
    void delete_WorkFlowAction_Validation_when_Id_is_negative() throws Exception {
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
    @Order(18)
    void update_WorkFlowAction() throws Exception {
        WorkFlowAction tt = workFlowActionRepository.save(getWorkFlowActionModel());
        WorkFlowActionDtoFull dto = getWorkFlowActionDtoFull();
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
            workFlowActionRepository.deleteById(id);
        }
    }

    @Test
    @Order(19)
    void update_WorkFlowAction_when_Request_Id_is_different_Dto_Id() throws Exception {
        WorkFlowAction tt = workFlowActionRepository.save(getWorkFlowActionModel());
        WorkFlowActionDtoFull dto = getWorkFlowActionDtoFull();
        dto.setName("another name");
        dto.setId(tt.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, tt.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof BadRequestException));
        } finally {
            workFlowActionRepository.deleteById(tt.getId());
        }
    }

    @Test
    @Order(20)
    void update_WorkFlowAction_when_Name_is_already_used_another_WorkFlowAction() throws Exception {
        WorkFlowAction tt = workFlowActionRepository.save(getWorkFlowActionModel());
        WorkFlowActionDtoFull dto = getWorkFlowActionDtoFull();
        dto.setId(tt.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, tt.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult -> assertNotNull(mvcResult.getResolvedException()));
        } finally {
            workFlowActionRepository.deleteById(tt.getId());
        }
    }

    @Test
    @Order(21)
    void update_WorkFlowAction_when_NotFound() throws Exception {
        WorkFlowActionDtoFull dto = getWorkFlowActionDtoFull();
        dto.setId(Long.MAX_VALUE);

        mockMvc.perform(
                putJson(UPDATE_PATH, Long.MAX_VALUE, dto))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }
}
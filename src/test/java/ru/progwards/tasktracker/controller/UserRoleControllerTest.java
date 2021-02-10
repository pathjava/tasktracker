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
import ru.progwards.tasktracker.dto.UserRoleDtoFull;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.UserRole;
import ru.progwards.tasktracker.repository.UserRoleRepository;

import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.progwards.tasktracker.objects.GetDtoFull.getUserRoleDtoFull;
import static ru.progwards.tasktracker.objects.GetModel.getUserRole;

/**
 * Тестирование методов контроллера UserRoleController
 *
 * @author Konstantin Kishkin
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class UserRoleControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private UserRoleRepository userRoleRepository;

    private static final String GET_PATH = "/rest/userRole/{id}";
    private static final String CREATE_PATH = "/rest/userRole/create";
    private static final String GET_LIST = "/rest/userRole/list";
    private static final String UPDATE_PATH = "/rest/userRole/{id}/update";
    private static final String DELETE_PATH = "/rest/userRole/{id}/delete";

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

    public static MockHttpServletRequestBuilder getListUriAndMediaType(String uri) {
        return get(uri)
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
    void create_UserRole() throws Exception {
        UserRoleDtoFull dto = getDtoFull();

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
            userRoleRepository.deleteById(id);
        }
    }

    private UserRoleDtoFull getDtoFull() {
        UserRoleDtoFull dto = getUserRoleDtoFull();

        return dto;
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    @Order(2)
    void create_UserRole_BadRequest_Validation_If_Id_is_NotNull() throws Exception {
        UserRoleDtoFull dto = getDtoFull();

        dto.setId(1L);
        mockMvcPerformPost(dto);
    }

    private void mockMvcPerformPost(UserRoleDtoFull dto) throws Exception {
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void getList_UserRole() throws Exception {
        UserRole userRole = getUserRole();
        userRole.setName("Администраторы");
        UserRole userRole2 = getUserRole();
        userRole2.setName("Пользователи");
        List<UserRole> userRoleList = List.of(userRole, userRole2);
        userRoleRepository.saveAll(userRoleList);

        try {
            mockMvc.perform(
                    getListUriAndMediaType(GET_LIST))
                    .andExpect(status().isOk());
        } finally {
            userRoleRepository.deleteAll(userRoleList);
        }
    }


    @Test
    void getList_UserRole_Validation_when_Id_is_negative() throws Exception {
        mockMvcPerformGet(GET_LIST, -1L);
    }

    private void mockMvcPerformGet(String getPath, long l) throws Exception {
        mockMvc.perform(
                getUriAndMediaType(getPath, l))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void getList_UserRole_when_return_Empty_List() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_LIST, Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void update_UserRole() throws Exception {
        UserRole userRole = getUserRole();
        UserRoleDtoFull dto = getDtoFull();
        dto.setName("Администраторы");
        dto.setId(userRole.getId());

        MvcResult result = mockMvc.perform(
                putJson(UPDATE_PATH, userRole.getId(), dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class));
        } finally {
            userRoleRepository.deleteById(id);
        }
    }

    @Test
    void update_UserRole_when_NotFound() throws Exception {
        UserRoleDtoFull dto = getDtoFull();
        dto.setId(Long.MAX_VALUE);

        mockMvc.perform(
                putJson(UPDATE_PATH, Long.MAX_VALUE, dto))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void delete_UserRole() {
        UserRole userRole = getUserRole();

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, userRole.getId()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            userRoleRepository.deleteById(userRole.getId());
        }
    }

    @Test
    void delete_UserRole_Validation_when_Id_is_negative() throws Exception {
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


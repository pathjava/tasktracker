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
import ru.progwards.tasktracker.dto.RelationTypeDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.repository.RelationTypeRepository;

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

/**
 * Тестирование методов контроллера RelationTypeController
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestPropertySource(locations = "classpath:application-dev.properties")
@ActiveProfiles("dev")
class RelationTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RelationTypeRepository relationTypeRepository;

    private static final String GET_PATH = "/rest/relationtype/{id}";
    private static final String GET_LIST_PATH = "/rest/relationtype/list";
    private static final String CREATE_PATH = "/rest/relationtype/create";
    private static final String DELETE_PATH = "/rest/relationtype/{id}/delete";
    private static final String UPDATE_PATH = "/rest/relationtype/{id}/update";

    public RelationTypeDtoFull getRelationTypeDto() {
        return new RelationTypeDtoFull(
                null,
                "relation name",
                null
        );
    }

    public RelationType getRelationType() {
        return new RelationType(
                null,
                "relation name",
                null,
                null
        );
    }

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

    public static MockHttpServletRequestBuilder getListUriAndMediaType(String uri) {
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
    @Order(1)
    void create_RelationType_without_counter_RelationType() throws Exception {
        MvcResult result = mockMvc.perform(
                postJson(CREATE_PATH, getRelationTypeDto()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.counterRelationId", equalTo(null)))
                    .andExpect(jsonPath("$.name", equalTo("relation name")));
        } finally {
            relationTypeRepository.deleteById(id);
        }
    }

    private Long getResultId(MvcResult result) throws UnsupportedEncodingException {
        String resultJson = result.getResponse().getContentAsString();
        return JsonPath.parse(resultJson).read("$.id", Long.class);
    }

    @Test
    @Order(2)
    void create_RelationType_with_counter_RelationType() throws Exception {
        RelationType counterType = getRelationType();
        counterType.setName("counter name");
        Long counterId = relationTypeRepository.save(counterType).getId();
        RelationTypeDtoFull dto = getRelationTypeDto();
        dto.setCounterRelationId(counterId);

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
                    .andExpect(jsonPath("$.counterRelationId", is(counterType.getId()), Long.class))
                    .andExpect(jsonPath("$.name", equalTo("relation name")));
        } finally {
            relationTypeRepository.deleteById(id);
        }
    }

    @Test
    @Order(3)
    void create_RelationType_BadRequest_Validation_If_Id_is_NotNull() throws Exception {
        RelationTypeDtoFull dto = getRelationTypeDto();
        dto.setId(1L);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(4)
    void create_RelationType_BadRequest_Validation_If_Name_is_Empty() throws Exception {
        RelationTypeDtoFull dto = getRelationTypeDto();
        dto.setName("");
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    @Order(5)
    void create_RelationType_BadRequest_Validation_If_Name_is_Null() throws Exception {
        RelationTypeDtoFull dto = getRelationTypeDto();
        dto.setName(null);
        mockMvc.perform(
                postJson(CREATE_PATH, dto))
                .andExpect(status().isInternalServerError())
                .andExpect(mvcResult -> assertNotNull(mvcResult.getResolvedException()));
    }

    @Test
    @Order(6)
    void get_RelationType() throws Exception {
        RelationType rt = relationTypeRepository.save(getRelationType());

        try {
            mockMvc.perform(
                    getUriAndMediaType(GET_PATH, rt.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(rt.getId()), Long.class))
                    .andExpect(jsonPath("$.name", equalTo("relation name")));
        } finally {
            relationTypeRepository.deleteById(rt.getId());
        }
    }

    @Test
    @Order(7)
    void get_RelationType_when_NotFound() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH,1L))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(8)
    void get_one_RelationType_when_Id_is_negative() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, -1L))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(9)
    void get_one_RelationType_when_Id_more_value_Long() throws Exception {
        mockMvc.perform(
                getUriAndMediaType(GET_PATH, Long.MAX_VALUE + 1))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(10)
    void getList_RelationType() throws Exception {
        RelationType one = getRelationType();
        one.setName("name one");
        RelationType two = getRelationType();
        two.setName("name two");
        List<RelationType> listType = List.of(one, two);
        relationTypeRepository.saveAll(listType);

        try {
            mockMvc.perform(
                    getListUriAndMediaType(GET_LIST_PATH))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].name", containsInAnyOrder("name one", "name two")));
        } finally {
            relationTypeRepository.deleteAll(listType);
        }
    }

    @Test
    @Order(11)
    void getList_RelationType_when_return_Empty_List() throws Exception {
        mockMvc.perform(
                getListUriAndMediaType(GET_LIST_PATH))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @Order(12)
    void delete_RelationType() {
        RelationType rt = relationTypeRepository.save(getRelationType());

        try {
            mockMvc.perform(
                    deleteUriAndMediaType(DELETE_PATH, rt.getId()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            relationTypeRepository.deleteById(rt.getId());
        }
    }

    @Test
    @Order(13)
    void delete_RelationType_when_Id_is_negative() throws Exception {
        mockMvc.perform(
                deleteUriAndMediaType(DELETE_PATH, -1L))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(14)
    void delete_RelationType_when_Id_more_value_Long() throws Exception {
        mockMvc.perform(
                deleteUriAndMediaType(DELETE_PATH, Long.MAX_VALUE + 1))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(15)
    void update_RelationType() throws Exception {
        RelationType rt = relationTypeRepository.save(getRelationType());
        RelationTypeDtoFull dto = getRelationTypeDto();
        dto.setName("updated name");
        dto.setId(rt.getId());

        MvcResult result = mockMvc.perform(
                putJson(UPDATE_PATH, rt.getId(), dto))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Long id = getResultId(result);

        try {
            mockMvc.perform(get(GET_PATH.replace("{id}", String.valueOf(id))))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(id), Long.class))
                    .andExpect(jsonPath("$.counterRelationId", equalTo(null)))
                    .andExpect(jsonPath("$.name", equalTo("updated name")));
        } finally {
            relationTypeRepository.deleteById(id);
        }
    }

    @Test
    @Order(16)
    void update_RelationType_when_Request_Id_is_different_Dto_Id() throws Exception {
        RelationType rt = relationTypeRepository.save(getRelationType());
        RelationTypeDtoFull dto = getRelationTypeDto();
        dto.setName("another name");
        dto.setId(rt.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, rt.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult ->
                            assertTrue(mvcResult.getResolvedException() instanceof BadRequestException));
        } finally {
            relationTypeRepository.deleteById(rt.getId());
        }
    }

    @Test
    @Order(17)
    void update_RelationType_when_Name_is_already_used_another_RelationType() throws Exception {
        RelationType rt = relationTypeRepository.save(getRelationType());
        RelationTypeDtoFull dto = getRelationTypeDto();
        dto.setId(rt.getId() + 1);

        try {
            mockMvc.perform(
                    putJson(UPDATE_PATH, rt.getId(), dto))
                    .andExpect(status().isBadRequest())
                    .andExpect(mvcResult -> assertNotNull(mvcResult.getResolvedException()));
        } finally {
            relationTypeRepository.deleteById(rt.getId());
        }
    }

    @Test
    @Order(18)
    void update_RelationType_when_NotFound() throws Exception {
        RelationTypeDtoFull dto = getRelationTypeDto();
        dto.setId(1L);

        mockMvc.perform(
                putJson(UPDATE_PATH, 1L, dto))
                .andExpect(status().isNotFound())
                .andExpect(mvcResult ->
                        assertTrue(mvcResult.getResolvedException() instanceof NotFoundException));
    }
}
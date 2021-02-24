package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.progwards.tasktracker.dto.*;
import ru.progwards.tasktracker.model.types.WorkFlowState;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class WorkFlowStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    static WorkFlowDtoPreview parentEntity = null;
    private boolean parentIsMine = true;
    private WorkFlowStatusDtoFull flowEntity = null;

    static final String CREATE_PATH = "/rest/workflowstatus/create";
    static final String GET_PATH = "/rest/workflowstatus/{id}";
    static final String GET_LIST_PATH = "/rest/workflowstatus/list";
    static final String UPDATE_PATH = "/rest/workflowstatus/{id}/update";
    static final String DELETE_PATH = "/rest/workflowstatus/{id}/delete";

    /**
     * Создать запрос на MVC контроллер
     *
     * @param method "GET" / "POST" / "DELETE"
     * @param uri    API URL
     * @param id     identifier to replace inside URL
     * @param body   request body to send
     * @return
     * @throws JsonProcessingException
     */
    public String makeRequest(String method, String uri, Long id, Object body, ResultMatcher resultMatcher) throws Exception {
        MockHttpServletRequestBuilder request1 = MockMvcRequestBuilders.request(
                HttpMethod.valueOf(method),
                uri.replace("{id}", String.valueOf(id))
        );
        RequestBuilder request2 = request1.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(body));

        ResultMatcher expectStatus = resultMatcher == null ? MockMvcResultMatchers.status().isOk() : resultMatcher;

        String result = mockMvc.perform(request2)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(expectStatus)
                .andReturn()
                .getResponse()
                .getContentAsString();

        return result;
    }

    public String makeRequest(String method, String uri, Long id, Object body) throws Exception {
        return makeRequest(method, uri, id, body, MockMvcResultMatchers.status().isOk());
    }

    public String makeRequest(String method, String uri) throws Exception {
        return makeRequest(method, uri, null, null, MockMvcResultMatchers.status().isOk());
    }

    static int testNo = 0;

    public static WorkFlowStatusDtoFull fullCreator() {
        WorkFlowStatusDtoFull result = new WorkFlowStatusDtoFull();
        testNo++;
        result.setName("testWFstatus" + testNo);
        result.setAlwaysAllow(true);
        result.setState(new WorkFlowStateDtoPreview(WorkFlowState.TO_DO.toString()));
        result.setWorkflow(parentEntity);
        return result;
    }

    private void checkIsError(String responce) {
        String failStart = "{\"timestamp\":";
        boolean isError = responce.startsWith(failStart);
        Assertions.assertFalse(isError, "The response have error body '" + responce + "'");
    }


    @Test
    @Order(0)
    void prepareParentEntity() throws Exception {
        String responce = makeRequest("GET", WorkFlowControllerTest.GET_LIST_PATH);
        checkIsError(responce);

        WorkFlowDtoPreview[] arr = objectMapper.readValue(responce, WorkFlowDtoPreview[].class);
        Assertions.assertTrue(arr.length == 0 || arr[0] instanceof WorkFlowDtoPreview, "Result array is wrong");

        for (WorkFlowDtoPreview el : arr) {
            if (el.getPattern()) {
                parentEntity = el;
                break;
            }
        }
        if (parentEntity == null) {
            WorkFlowDtoFull dto = WorkFlowControllerTest.fullCreator();
            dto.setPattern(true);
            String responce2 = makeRequest("POST", WorkFlowControllerTest.CREATE_PATH, dto.getId(), dto);
            checkIsError(responce2);
            WorkFlowDtoFull full = objectMapper.readValue(responce2, WorkFlowDtoFull.class);
            parentEntity = WorkFlowControllerTest.fullToPreview(full);
        }
        Assertions.assertNotNull(parentEntity, "Cannot resolve parent entity WorkFlow");
        flowEntity = fullCreator();
    }

    @Test
    @Order(10)
    void create() throws Exception {
        Assertions.assertNotNull(flowEntity, "flowEntity was not set. Cannot start test");

        WorkFlowStatusDtoFull dto = flowEntity;
        String responce = makeRequest("POST", CREATE_PATH, dto.getId(), dto);
        checkIsError(responce);
        flowEntity = objectMapper.readValue(responce, WorkFlowStatusDtoFull.class);

        Assertions.assertNotNull(flowEntity.getId(), "Identifier was not set. Entity is invalid");
        Assertions.assertEquals(dto.getName(), flowEntity.getName());
    }

    @Test
    @Order(20)
    void get() throws Exception {
        Assertions.assertNotNull(flowEntity, "flowEntity was not set. Cannot start test");

        WorkFlowStatusDtoFull dto = flowEntity;
        String responce = makeRequest("GET", GET_PATH, dto.getId(), null);
        checkIsError(responce);
        flowEntity = objectMapper.readValue(responce, WorkFlowStatusDtoFull.class);

        Assertions.assertNotNull(flowEntity.getId(), "Identifier was not set. Entity is invalid");
        Assertions.assertEquals(dto.getName(), flowEntity.getName());
    }

    @Test
    @Order(30)
    void getList() throws Exception {
        Assertions.assertNotNull(flowEntity, "flowEntity was not set. Cannot start test");

        String responce = makeRequest("GET", GET_LIST_PATH);
        checkIsError(responce);

        WorkFlowStatusDtoPreview[] arr = objectMapper.readValue(responce, WorkFlowStatusDtoPreview[].class);
        Assertions.assertTrue(arr[0] instanceof WorkFlowStatusDtoPreview, "Result array is wrong");

        boolean found = false;
        for (WorkFlowStatusDtoPreview el : arr) {
            if (el.getId().equals(flowEntity.getId())) {
                found = true;
                break;
            }
        }
        Assertions.assertTrue(found, "Array does not have previously added entity");
    }

    @Test
    @Order(40)
    void updateNotExists() throws Exception {
        WorkFlowStatusDtoFull dto = fullCreator();
        dto.setId(Long.MAX_VALUE);
        String responce = makeRequest("POST", UPDATE_PATH, dto.getId(), dto,
                MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(50)
    void updateName() throws Exception {
        Assertions.assertNotNull(flowEntity, "flowEntity was not set. Cannot start test");
        Assertions.assertNotNull(flowEntity.getId(), "Identifier was not set. Cannot start test");

        WorkFlowStatusDtoFull dto = flowEntity;
        dto.setName(dto.getName() + " renamed");
        String responce = makeRequest("POST", UPDATE_PATH, dto.getId(), dto);
    }

    @Test
    @Order(60)
    void updateAlwaysAllowToNull_validation() throws Exception {
        Assertions.assertNotNull(flowEntity, "flowEntity was not set. Cannot start test");
        Assertions.assertNotNull(flowEntity.getId(), "Identifier was not set. Cannot start test");

        Boolean pattern_save = flowEntity.getAlwaysAllow();
        flowEntity.setAlwaysAllow(null);
        String responce = makeRequest("POST", UPDATE_PATH, flowEntity.getId(), flowEntity,
                MockMvcResultMatchers.status().is4xxClientError());
        flowEntity.setAlwaysAllow(pattern_save);
    }

    @Test
    @Order(70)
    void updateNameToEmpty_validation() throws Exception {
        Assertions.assertNotNull(flowEntity, "flowEntity was not set. Cannot start test");
        Assertions.assertNotNull(flowEntity.getId(), "Identifier was not set. Cannot start test");

        String name_save = flowEntity.getName();
        flowEntity.setName("");
        String responce = makeRequest("POST", UPDATE_PATH, flowEntity.getId(), flowEntity,
                MockMvcResultMatchers.status().is4xxClientError());
        flowEntity.setName(name_save);
    }

    @Test
    @Order(100)
    void deleteNegative_validation() throws Exception {
        Assertions.assertNotNull(flowEntity, "flowEntity was not set. Cannot start test");
        Assertions.assertNotNull(flowEntity.getId(), "Identifier was not set. Cannot start test");

        String responce = makeRequest("DELETE", DELETE_PATH, -flowEntity.getId(), null,
                MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(110)
    void delete() throws Exception {
        Assertions.assertNotNull(flowEntity, "flowEntity was not set. Cannot start test");
        Assertions.assertNotNull(flowEntity.getId(), "Identifier was not set. Cannot start test");

        String responce = makeRequest("DELETE", DELETE_PATH, flowEntity.getId(), null);
    }

}
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
import ru.progwards.tasktracker.dto.WorkFlowDtoFull;
import ru.progwards.tasktracker.dto.WorkFlowStateDtoPreview;
import ru.progwards.tasktracker.dto.WorkFlowStateDtoPreview;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class WorkFlowStateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String GET_LIST_PATH = "/rest/workflowstate/list";

    /**
     * Создать запрос на MVC контроллер
     *
     * @param method "GET" / "POST" / "DELETE"
     * @param uri API URL
     * @param id identifier to replace inside URL
     * @param body request body to send
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

        ResultMatcher expectStatus = resultMatcher==null ? MockMvcResultMatchers.status().isOk() : resultMatcher;

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

    private void checkIsError(String responce) {
        String failStart = "{\"timestamp\":";
        boolean isError = responce.startsWith(failStart);
        Assertions.assertFalse(isError, "The response have error body '"+responce+"'");
    }

    @Test
    void getList() throws Exception {
        String responce = makeRequest("GET", GET_LIST_PATH);
        checkIsError(responce);

        WorkFlowStateDtoPreview[] arr = objectMapper.readValue(responce, WorkFlowStateDtoPreview[].class);
        Assertions.assertTrue(arr[0] instanceof WorkFlowStateDtoPreview, "Result array is wrong");
        Assertions.assertTrue(arr.length > 1, "Array of state is too short: "+arr.length);
    }

}
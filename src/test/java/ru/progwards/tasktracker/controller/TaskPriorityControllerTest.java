package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.progwards.tasktracker.dto.TaskPriorityDtoFull;
import ru.progwards.tasktracker.dto.TaskPriorityDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.TaskPriority;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.GetService;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тест TaskPriorityController
 * @author Pavel Khovaylo
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
//подключаем к тестированию базу данных H2
@TestPropertySource(locations = {"/application-test.properties"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class TaskPriorityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GetService<Long, TaskPriority> getService;

    @Autowired
    private GetListService<TaskPriority> getListService;

    @Autowired
    private Converter<TaskPriority, TaskPriorityDtoFull> dtoFullConverter;

    @Autowired
    private Converter<TaskPriority, TaskPriorityDtoPreview> dtoPreviewConverter;

    @Autowired
    private ObjectMapper objectMapper;

    private Long createdId;


    @Test
    @Order(1)
    public void create() throws Exception {
        TaskPriorityDtoFull taskPriorityDtoFull = new TaskPriorityDtoFull(null, "TASK", 1);

        String json = objectMapper.registerModule(new JavaTimeModule()).
                writeValueAsString(taskPriorityDtoFull);

        mockMvc.perform(post("/rest/taskpriority/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    @Order(2)
    public void getTaskPriority() throws Exception {
        TaskPriorityDtoFull taskPriorityDtoFull = dtoFullConverter.toDto(getService.get(1L));
        String expectedJsonResponse = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(taskPriorityDtoFull);

        String url = "/rest/taskpriority/1";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonResponse))
                .andExpect(jsonPath("$.name", equalTo(taskPriorityDtoFull.getName())));
    }

    @Test
    @Order(3)
    public void getTaskPriorityList() throws Exception {
        List<TaskPriorityDtoPreview> list = getListService.getList().stream()
                .map(dtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        String url = "/rest/taskpriority/list";
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(list);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    @Order(4)
    public void update() throws Exception {
        TaskPriority taskPriority = getService.get(1L);
        String newName = "BUG";
        taskPriority.setName(newName);

        TaskPriorityDtoFull taskPriorityDtoFull = dtoFullConverter.toDto(taskPriority);
        String updated = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(taskPriorityDtoFull);

        String urlUpdate = "/rest/taskpriority/1/update";

        mockMvc.perform(post(urlUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updated))
                .andDo(print())
                .andExpect(status().isOk());

        String urlGet = "/rest/taskpriority/1";

        mockMvc.perform(get(urlGet))
                .andExpect(status().isOk())
                .andExpect(content().json(updated))
//                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.name", equalTo(newName)));
    }

    @Test
    @Order(5)
    public void deleteTest() throws Exception {
        String url = "/rest/taskpriority/4/delete";
        mockMvc.perform(post(url)).andExpect(status().isOk());

        String urlGet = "/rest/taskpriority/4";

        mockMvc.perform(get(urlGet))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OperationIsNotPossibleException));
    }

    @Test
    @Order(6)
    public void checkingId() throws Exception {
        mockMvc.perform(get("/rest/taskpriority/" + (Long.MAX_VALUE + 1)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(7)
    public void checkingUpdate() throws Exception {
        TaskPriority taskPriority = getService.get(5L);
        TaskPriorityDtoFull taskPriorityDtoFull = dtoFullConverter.toDto(taskPriority);
        String updatedTaskPriority = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(taskPriorityDtoFull);

        String url = "/rest/taskpriority/7/update";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTaskPriority))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OperationIsNotPossibleException))
                .andExpect(result -> assertEquals("Impossible to update task-priority", result.getResolvedException().getMessage()));
    }
}

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.dto.UserDtoFull;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RefreshService;
import ru.progwards.tasktracker.service.RemoveService;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.ZonedDateTime;
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
 * Тест ProjectController
 * @author Pavel Khovaylo
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
//подключаем к тестированию базу данных H2
@TestPropertySource(locations = {"/application-test.properties"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectController projectController;

    @Autowired
    private Converter<User, UserDtoFull> converterUserDtoFull;

    @Autowired
    private Converter<User, UserDtoPreview> converterUserDtoPreview;

    @Autowired
    private Converter<Project, ProjectDtoFull> converterProjectDtoFull;

    @Autowired
    private Converter<Project, ProjectDtoPreview> converterProjectDtoPreview;

    @Autowired
    private GetListService<Project> projectGetListService;

    @Autowired
    private GetService<Long, Project> projectGetService;

    @Autowired
    private GetService<Long, User> userGetService;

    @Autowired
    private RefreshService<Project> refreshService;

    @Autowired
    private RemoveService<Project> removeService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private TestRestTemplate testRestTemplate;

//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    @Test
    @Order(1)
    public void create() throws Exception {

//        UserDtoFull userDto = new UserDtoFull(null, "User name", "name@gmail.com",
//                "12345", "12345", new ArrayList<>());
//        UserDtoFull created = userController.createUser(userDto).getBody();
//        User user = converterUserDtoFull.toModel(created);

        User user = userGetService.get(1L);

        ProjectDtoFull projectDto = new ProjectDtoFull(null, "Name of project",
                "something", "PR1", converterUserDtoPreview.toDto(user),
                ZonedDateTime.now());

        String json = objectMapper.registerModule(new JavaTimeModule()).
                writeValueAsString(projectDto);

        mockMvc.perform(post("/rest/project/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        Project project = projectRepository.findByPrefix(projectDto.getPrefix()).get();

        mockMvc.perform(get("/rest/project/" + project.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Name of project")));
    }

    @Test
    @Order(2)
    public void getProject() throws Exception {
        //тестирование с фильтрами Spring Security
//        ProjectDtoFull projectDtoFull = converterProjectDtoFull.toDto(projectGetService.get(21L));
//
//        String url = "/rest/project/21";
//        ResponseEntity<ProjectDtoFull> responseEntity = testRestTemplate
//                .withBasicAuth("admin", "XJpFcJrJnQ8c4Apm7016")
//                .getForEntity(url, ProjectDtoFull.class);
//
//        String actualJsonResponse = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(responseEntity.getBody());
//        String expectedJsonResponse = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(projectDtoFull);
//
//        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

        ProjectDtoFull projectDtoFull = converterProjectDtoFull.toDto(projectGetService.get(22L));
        String expectedJsonResponse = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(projectDtoFull);

        String url = "/rest/project/22";
//        MvcResult mvcResult =
                mockMvc.perform(get(url)).andExpect(status().isOk())
                .andExpect(content().json(expectedJsonResponse))
                .andExpect(jsonPath("$.name", equalTo(projectDtoFull.getName())))
//                .andReturn()
                ;

//        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

//        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }


    @Test
    @Order(3)
    public void getProjectList() throws Exception {
        List<ProjectDtoPreview> projects = projectGetListService.getList().stream()
                .map(converterProjectDtoPreview::toDto)
                .collect(Collectors.toList());

        String url = "/rest/project/list";
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(projects);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
    }

    @Test
    @Order(4)
    public void update() throws Exception {
        Project project = projectGetService.get(22L);
//        String oldDesc = project.getDescription();
        String newDesc = "New description 2";
        project.setDescription(newDesc);

        ProjectDtoFull projectDtoFull = converterProjectDtoFull.toDto(project);
        String updatedProject = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(projectDtoFull);


        String url = "/rest/project/22/update";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedProject))
                .andDo(print())
                .andExpect(status().isOk());

        Project getUpdatedFromRepo = projectGetService.get(22L);
        assertEquals(newDesc, getUpdatedFromRepo.getDescription());
//        assertThat(newDesc).isEqualToIgnoringWhitespace(updated.getDescription());
    }

    @Test
    @Order(5)
    public void delete() throws Exception {
        String url = "/rest/project/22/delete";
        mockMvc.perform(post(url)).andExpect(status().isOk());

        Project remote = projectGetService.get(22L);
        assertTrue(remote.isDeleted());
    }

    @Test
    @Order(6)
    public void checkingId() throws Exception {
        mockMvc.perform(get("/rest/project/" + (Long.MAX_VALUE + 1)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    @Order(7)
    public void uniquePrefix() throws Exception {
        User user = userGetService.get(1L);

        ProjectDtoFull projectDto = new ProjectDtoFull(null, "Project name 1",
                "something", "TEST", converterUserDtoPreview.toDto(user),
                ZonedDateTime.now());

        String json = objectMapper.registerModule(new JavaTimeModule()).
                writeValueAsString(projectDto);

        mockMvc.perform(post("/rest/project/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    @Order(8)
    public void checkingUpdate() throws Exception {
        Project project = projectGetService.get(22L);
        ProjectDtoFull projectDtoFull = converterProjectDtoFull.toDto(project);
        String updatedProject = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(projectDtoFull);

        String url = "/rest/project/21/update";
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedProject))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OperationIsNotPossibleException))
                .andExpect(result -> assertEquals("Impossible to update project", result.getResolvedException().getMessage()));
    }
}
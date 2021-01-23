package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.dto.ProjectDtoPreview;
import ru.progwards.tasktracker.dto.UserDtoFull;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.GetService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тест ProjectController
 * @author Pavel Khovaylo
 */
@SpringBootTest
@AutoConfigureMockMvc
//подключаем к тестированию базу данных H2
@TestPropertySource(locations = {"/application-test.properties"})
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectController projectController;

    @Autowired
    private UserController userController;

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
    private ProjectRepository projectRepository;

    @Test
    public void loadedController() {
        Assertions.assertNotNull(projectController);
    }

    @Test
    public void getProjectTest() throws Exception {
        ProjectDtoFull projectDtoFull = converterProjectDtoFull.toDto(projectGetService.get(20L));
        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projectDtoFull);

        mockMvc.perform(get("/rest/project/20"))
                .andExpect(status().isOk())
                .andExpect(content().json(json))
                .andExpect(jsonPath("$.name", equalTo("Name of project")));
    }


    @Test
    public void getProjectsTest() throws Exception {
        List<ProjectDtoPreview> projects = projectGetListService.getList().stream()
                .map(converterProjectDtoPreview::toDto)
                .collect(Collectors.toList());

        String json = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projects);

        mockMvc.perform(get("/rest/project/list")).
                andExpect(status().isOk()).
                andExpect(content().json(json));
    }

    @Test
    public void createTest() throws Exception {

//        UserDtoFull userDto = new UserDtoFull(null, "User name", "name@gmail.com",
//                "12345", "12345", new ArrayList<>());
//        UserDtoFull created = userController.createUser(userDto).getBody();
//        User user = converterUserDtoFull.toModel(created);

        User user = userGetService.get(1L);

        ProjectDtoFull projectDto = new ProjectDtoFull(null, "Name of project",
                "something", "PR1", converterUserDtoPreview.toDto(user),
                ZonedDateTime.now());

        String json = new ObjectMapper().registerModule(new JavaTimeModule()).
                writeValueAsString(projectDto);

        mockMvc.perform(post("/rest/project/create").
                contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andDo(print()).
                andExpect(status().is2xxSuccessful());

        Project project = projectRepository.findByPrefix(projectDto.getPrefix()).get();

        mockMvc.perform(get("/rest/project/" + project.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Name of project")));
    }
}

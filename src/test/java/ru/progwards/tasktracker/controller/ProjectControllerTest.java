package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.ProjectDto;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест ProjectController
 * @author Pavel Khovaylo
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectController controller;

    @Autowired
    private Converter<Project, ProjectDto> converter;

    @Autowired
    private GetListService<Project> projectGetListService;

    @Autowired
    private GetService<Long, Project> projectGetService;

    @Test
    public void loadedController() {
        Assertions.assertNotNull(controller);
    }

    @Test
    public void getProjectsTest() throws Exception {
        Collection<ProjectDto> projectDtos = projectGetListService.getList().stream().
                map(e -> converter.toDto(e)).collect(Collectors.toList());
        String stringJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projectDtos);

        mockMvc.perform(get("/rest/project/list")).
                andExpect(status().isOk()).
                andExpect(content().json(stringJson));
    }

    @Test
    public void getProject() throws Exception {
        ProjectDto projectDto = converter.toDto(projectGetService.get(4L));
        String stringJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projectDto);

        mockMvc.perform(get("/rest/project/4")).
                andExpect(status().isOk()).
                andExpect(content().json(stringJson));
    }

    @Test
    public void NotFoundProjectExceptionTest() throws Exception {
        mockMvc.perform(get("/rest/project/100")).andExpect(content().
                string("Not found a project with id=100"));

        mockMvc.perform(post("/rest/project/100/delete")).andExpect(content().
                string("Not found a project with id=100"));
    }
}

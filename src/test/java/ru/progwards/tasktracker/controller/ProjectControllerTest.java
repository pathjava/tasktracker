package ru.progwards.tasktracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.ProjectDto;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.Project;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectController controller;

    @Autowired
    private Repository<Long, ProjectEntity> repository;

    @Autowired
    private Converter<Project, ProjectDto> converter;

    @Autowired
    private GetListService<Project> projectGetListService;

    @Test
    public void loadedController() {
        Assertions.assertNotNull(controller);
    }

    @Test
    public void getProjectsTest() throws Exception {
        Collection<ProjectDto> projectDtos = projectGetListService.getList().stream().
                map(e -> converter.toDto(e)).collect(Collectors.toList());
        String json = new ObjectMapper().writeValueAsString(projectDtos);

        mockMvc.perform(get("/rest/project/list")).
                andExpect(status().isOk()).
                andExpect(content().json(json));
    }

    @Test
    public void getProject() throws Exception {
        ProjectEntity entity = new ProjectEntity(7L, "name", "desc", "PRO", 7L,
                24124141453L, 7L, 0L);

        repository.create(entity);

        String json = new ObjectMapper().writeValueAsString(entity);

        mockMvc.perform(get("/rest/project/7")).
                andExpect(status().isOk()).
                andExpect(content().json(json));
    }

    @Test
    public void createTest() throws Exception {
        ProjectEntity entity = new ProjectEntity(5L, "name5", "description5", "N", 5L,
                1603316132000L, 5L, 0L);

        String json = new ObjectMapper().writeValueAsString(entity);

        mockMvc.perform(post("/rest/project/create").
                content("{\n" +
                        "    \"id\": 5,\n" +
                        "    \"name\": \"name5\",\n" +
                        "    \"description\": \"description5\",\n" +
                        "    \"prefix\": \"N\",\n" +
                        "    \"ownerId\": 5,\n" +
                        "    \"created\": 1603316132000,\n" +
                        "    \"workFlowId\": 5,\n" +
                        "    \"lastTaskCode\": 0\n" +
                        "}").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().is2xxSuccessful()).andExpect(content().json(json));

        mockMvc.perform(get("/rest/project/5")).
                andExpect(jsonPath("$.name").value("name5")).
                andExpect(jsonPath("$.created").value("1603316132000"));
    }

    @Test
    public void updateTest() throws Exception {
        ProjectEntity entity = new ProjectEntity(6L, "name6", "description6", "N", 6L,
                16033161325300L, 6L, 0L);
        repository.create(entity);

        String json = new ObjectMapper().writeValueAsString(entity);

        mockMvc.perform(post("/rest/project/6/update").
                content("{\n" +
                        "    \"name\": \"name6810\",\n" +
                        "    \"description\": \"description project 6\",\n" +
                        "    \"prefix\": \"N\",\n" +
                        "    \"ownerId\": 6,\n" +
                        "    \"created\": 16033161325900,\n" +
                        "    \"workFlowId\": 6,\n" +
                        "    \"lastTaskCode\": 0\n" +
                        "}").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andDo(print()).andExpect(status().isOk());

        mockMvc.perform(get("/rest/project/6")).
                andExpect(jsonPath("$.name").value("name6810")).
                andExpect(jsonPath("$.description").value("description project 6"));
    }

    @Test
    public void deleteTest() throws Exception {
        Collection<ProjectEntity> entities = repository.get();
        String json = new ObjectMapper().writeValueAsString(entities);

        ProjectEntity entity = new ProjectEntity(8L, "name8", "description8", "N", 8L,
                16033161325300L, 8L, 0L);
        repository.create(entity);

        mockMvc.perform(post("/rest/project/8/delete")).andExpect(status().isOk());

//        mockMvc.perform(get("/rest/project/list")).andExpect(content().json(json));
    }

    @Test
    public void updateOneFieldTest() throws Exception {
        ProjectEntity entity = new ProjectEntity(8L, "name8", "description8", "N", 8L,
                16033161325300L, 8L, 0L);
        repository.create(entity);

        mockMvc.perform(post("/rest/project/8/update1field").
                content("{\n" +
                "    \"newValue\": \"name project 8\",\n" +
                "    \"fieldName\": \"name\"\n" +
                "}").
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());

        mockMvc.perform(get("/rest/project/8")).
                andExpect(jsonPath("$.name").value("name project 8"));
    }

    @Test
    public void NotFoundProjectExceptionTest() throws Exception {
        mockMvc.perform(get("/rest/project/100")).andExpect(content().
                string("Not found a project with id=100"));

        mockMvc.perform(post("/rest/project/100/delete")).andExpect(content().
                string("Not found a project with id=100"));
    }
}

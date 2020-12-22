package ru.progwards.tasktracker.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.ProjectDtoFull;
import ru.progwards.tasktracker.dto.UserDtoPreview;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.User;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
    private Converter<Project, ProjectDtoFull> converter;

    @Autowired
    private GetListService<Project> projectGetListService;

    @Autowired
    private GetService<Long, Project> projectGetService;

    @Autowired
    private GetService<Long, User> userGetService;

    @Autowired
    private Converter<User, UserDtoPreview> converterUserDtoPreview;

    @Test
    public void loadedController() {
        Assertions.assertNotNull(controller);
    }

//    @Test
//    public void getProjectsTest() throws Exception {
//        Collection<ProjectDtoFull> projectDtos = projectGetListService.getList().stream().
//                map(e -> converter.toDto(e)).collect(Collectors.toList());
//        String stringJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projectDtos);
//
//        mockMvc.perform(get("/rest/project/list")).
//                andExpect(status().isOk()).
//                andExpect(content().json(stringJson));
//    }
//
//    @Test
//    public void getProject() throws Exception {
//        ProjectDtoFull projectDto = converter.toDto(projectGetService.get(4L));
//        String stringJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(projectDto);
//
//        mockMvc.perform(get("/rest/project/4")).
//                andExpect(status().isOk()).
//                andExpect(content().json(stringJson));
//    }
//
//    @Test
//    public void NotFoundProjectExceptionTest() throws Exception {
//        mockMvc.perform(get("/rest/project/100")).andExpect(content().
//                string("Not found a project with id=100"));
//
//        mockMvc.perform(post("/rest/project/100/delete")).andExpect(content().
//                string("Not found a project with id=100"));
//    }

    @Test
    public void create() {
//        System.out.println(userGetService.get(0L));
//        System.out.println(userGetService.get(1L));
//        System.out.println(userGetService.get(2L));

        List<ProjectDtoFull> projectDtoFullList = new ArrayList<>(List.of(
                new ProjectDtoFull(null, "name1", "desc1", "prefix1", converterUserDtoPreview.toDto(userGetService.get(0L)), ZonedDateTime.now()),
                new ProjectDtoFull(null, "name2", "desc2", "prefix2", converterUserDtoPreview.toDto(userGetService.get(1L)), ZonedDateTime.now()),
                new ProjectDtoFull(null, "name3", "desc3", "prefix3", converterUserDtoPreview.toDto(userGetService.get(2L)), ZonedDateTime.now())
                ));

        projectDtoFullList.forEach(e -> controller.create(e));
    }
}

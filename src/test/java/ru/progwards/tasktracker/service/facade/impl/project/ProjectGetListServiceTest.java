package ru.progwards.tasktracker.service.facade.impl.project;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class ProjectGetListServiceTest {

    @Mock
    private GetListService<Project> projectGetListService;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getListTest() {
        User user = new User();
        user.setId(1L);

        List<Project> projectList = List.of(
                new Project(1L, "name1", "desc1", "prefix", user, ZonedDateTime.now(), new ArrayList<>(), 0L),
                new Project(2L, "name2", "desc2", "prefix", user, ZonedDateTime.now(), new ArrayList<>(), 0L)
        );

        Mockito.when(projectGetListService.getList()).thenReturn(projectList);

        Collection<Project> projects = projectGetListService.getList();

        Assertions.assertNotNull(projects);
        Assertions.assertEquals(2, projects.size());

        Collection<String> names = projects.stream().map(Project::getName).collect(Collectors.toList());
        MatcherAssert.assertThat(names, Matchers.containsInAnyOrder("name1", "name2"));
    }
}
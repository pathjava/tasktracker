package ru.progwards.tasktracker.service.facade.impl.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.converter.impl.ProjectConverter;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
public class ProjectCreateServiceTest {

    @Mock
    private JsonHandler<Long, ProjectEntity> projectEntityJsonHandler;

    @InjectMocks
    private ProjectEntityRepository repository = Mockito.spy(ProjectEntityRepository.class);

    @InjectMocks
    private ProjectConverter converter = Mockito.spy(ProjectConverter.class);

    @InjectMocks
    private ProjectCreateService projectCreateService;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTest() {
        Mockito.when(projectEntityJsonHandler.getMap()).thenReturn(new ConcurrentHashMap<>());

        User user = new User();
        user.setId(1L);

        Project model = new Project(1L, "name", "desc", "prefix", user, ZonedDateTime.now(), new ArrayList<>(), 0L);
        projectCreateService.create(model);

        ProjectEntity entity = repository.get(model.getId());

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(model.getName(), entity.getName());
    }
}

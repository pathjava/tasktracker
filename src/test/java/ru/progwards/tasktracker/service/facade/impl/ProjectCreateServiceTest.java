package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@SpringBootTest
public class ProjectCreateServiceTest {

    @Autowired
    private ProjectCreateService projectCreateService;

    @Autowired
    private ProjectEntityRepository repository;

    @Test
    public void createService() {
        Project model = new Project(100L, "project100", "desc", new User(1L),
                ZonedDateTime.now(), new WorkFlow(1L), new ArrayList<>());

        projectCreateService.create(model);

        Assertions.assertEquals("project100", repository.get(model.getId()).getName());
    }
}

package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.service.facade.impl.project.ProjectCreateService;

@SpringBootTest
public class ProjectCreateServiceTest {

    @Autowired
    private ProjectCreateService projectCreateService;

    @Autowired
    private ProjectEntityRepository repository;

}

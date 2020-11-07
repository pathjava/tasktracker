package ru.progwards.tasktracker.service.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.Repository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.Project;

@SpringBootTest
public class ProjectCreateServiceTest {

    @Autowired
    private CreateService<Project> projectCreateService;

    @Autowired
    private Repository<Long, ProjectEntity> repository;

}

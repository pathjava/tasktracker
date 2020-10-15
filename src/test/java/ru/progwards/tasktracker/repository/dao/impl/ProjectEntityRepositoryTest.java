package ru.progwards.tasktracker.repository.dao.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ProjectEntityRepositoryTest {
    @InjectMocks
    private ProjectEntityRepository repository;
    @Mock
    private JsonHandlerProjectEntity jsonHandlerProjectEntity;

    @Test
    public void createTest() {
        for (long i = 0; i < 10; i++) {
            repository.create(new ProjectEntity(i, "name"+i, "description"+i, i));
        }
        Mockito.verify(jsonHandlerProjectEntity, Mockito.times(10)).write();
    }
}

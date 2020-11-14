package ru.progwards.tasktracker.service.facade.impl;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.dao.impl.ProjectEntityRepository;
import ru.progwards.tasktracker.repository.dao.impl.TaskPriorityEntityRepository;
import ru.progwards.tasktracker.repository.entity.ProjectEntity;
import ru.progwards.tasktracker.repository.entity.TaskPriorityEntity;
import ru.progwards.tasktracker.service.converter.impl.ProjectConverter;
import ru.progwards.tasktracker.service.converter.impl.TaskPriorityConverter;
import ru.progwards.tasktracker.service.facade.GetListService;
import ru.progwards.tasktracker.service.facade.impl.project.ProjectCreateService;
import ru.progwards.tasktracker.service.vo.Project;
import ru.progwards.tasktracker.service.vo.TaskPriority;
import ru.progwards.tasktracker.service.vo.User;
import ru.progwards.tasktracker.service.vo.WorkFlow;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TaskPriorityServiceTest {

    @Mock
    private JsonHandler<Long, TaskPriority> taskPriorityJsonHandler;

    @InjectMocks
    private TaskPriorityEntityRepository repository = Mockito.spy(TaskPriorityEntityRepository.class);

    @InjectMocks
    private TaskPriorityConverter converter = Mockito.spy(TaskPriorityConverter.class);

    @InjectMocks
    private TaskPriorityService taskPriorityService;

    @Mock
    private GetListService<TaskPriority> taskPriorityGetListService;

    @BeforeEach
    public void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTest() {
        Mockito.when(taskPriorityJsonHandler.getMap()).thenReturn(new ConcurrentHashMap<>());

        TaskPriority model = new TaskPriority(1L, "name", 1);
        taskPriorityService.create(model);

        TaskPriorityEntity entity = repository.get(model.getId());

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(model.getName(), entity.getName());
    }

    @Test
    public void getListTest() {
        List<TaskPriority> taskPriorityList = List.of(
                new TaskPriority(1L, "name1", 1),
                new TaskPriority(2L, "name2", 2)
        );

        Mockito.when(taskPriorityGetListService.getList()).thenReturn(taskPriorityList);

        Collection<TaskPriority> taskPriorities = taskPriorityGetListService.getList();

        Assertions.assertNotNull(taskPriorities);
        Assertions.assertEquals(2, taskPriorities.size());

        Collection<String> names = taskPriorities.stream().map(TaskPriority::getName).collect(Collectors.toList());
        MatcherAssert.assertThat(names, Matchers.containsInAnyOrder("name1", "name2"));
    }
}

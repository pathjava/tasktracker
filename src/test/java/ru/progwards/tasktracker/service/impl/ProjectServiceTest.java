package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.User;
import ru.progwards.tasktracker.repository.ProjectRepository;
import ru.progwards.tasktracker.repository.UserRepository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тест ProjectService
 * @author Pavel Khovaylo
 */
@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User getUserForTest() {
        User user = new User(1L, "Kirill", "kirill@gmail.com", "12345", new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        return userService.get(1L);
    }

    @Test
    public void getList() {
        List<Project> projectList = new ArrayList<>();

        for (long i = 0; i < 5; i++) {
            Project project = new Project(i, "Name" + i, "something", "P" + i, getUserForTest(),
                    ZonedDateTime.now(), new ArrayList<>(), new ArrayList<>(), 0L, false);
            projectList.add(project);
        }

        when(projectRepository.getAllByDeletedIsFalse()).thenReturn(projectList);
        assertEquals(projectList, projectService.getList());
        verify(projectRepository, times(1)).getAllByDeletedIsFalse();
    }

    @Test
    public void get() {
        Project project = new Project(1L, "Name 1", "something", "P1", getUserForTest(),
                ZonedDateTime.now(), new ArrayList<>(), new ArrayList<>(), 0L, false);

        when(projectRepository.findByIdAndDeletedIsFalse(1L)).thenReturn(Optional.of(project));
        assertEquals(project, projectService.get(1L));
        verify(projectRepository, times(1)).findByIdAndDeletedIsFalse(1L);
    }

    @Test
    public void create() {
        Project project = new Project(null, "Name 1", "something", "P1", getUserForTest(),
                ZonedDateTime.now(), new ArrayList<>(), new ArrayList<>(), 0L, false);

        projectService.create(project);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void refresh() {
        Project project = new Project(1L, "Name 1", "something", "P1", getUserForTest(),
                ZonedDateTime.now(), new ArrayList<>(), new ArrayList<>(), 0L, false);

        when(projectRepository.findByIdAndDeletedIsFalse(1L)).thenReturn(Optional.of(project));

        projectService.refresh(project);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void remove() {
        Project project = new Project(1L, "Name 1", "something", "P1", getUserForTest(),
                ZonedDateTime.now(), new ArrayList<>(), new ArrayList<>(), 0L, false);

        projectService.remove(project);
        verify(projectRepository, times(1)).save(project);
    }
}
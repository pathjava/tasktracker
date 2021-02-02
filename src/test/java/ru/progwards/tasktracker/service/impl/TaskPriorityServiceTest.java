package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.model.TaskPriority;
import ru.progwards.tasktracker.repository.TaskPriorityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тест TaskPriorityService
 * @author Pavel Khovaylo
 */
@SpringBootTest
public class TaskPriorityServiceTest {
    @Mock
    private TaskPriorityRepository taskPriorityRepository;

    @InjectMocks
    private TaskPriorityService taskPriorityService;

    @Test
    public void getList() {
        List<TaskPriority> taskPriorityList = new ArrayList<>();
        taskPriorityList.add(new TaskPriority(1L, "Low", 1, new ArrayList<>()));
        taskPriorityList.add(new TaskPriority(2L, "Lowest", 2, new ArrayList<>()));
        taskPriorityList.add(new TaskPriority(3L, "High", 3, new ArrayList<>()));
        taskPriorityList.add(new TaskPriority(4L, "Highest", 4, new ArrayList<>()));

        when(taskPriorityRepository.findAll()).thenReturn(taskPriorityList);
        assertEquals(taskPriorityList, taskPriorityService.getList());
        verify(taskPriorityRepository, times(1)).findAll();
    }

    @Test
    public void get() {
        TaskPriority taskPriority = new TaskPriority(3L, "High", 3, new ArrayList<>());

        when(taskPriorityRepository.findById(3L)).thenReturn(Optional.of(taskPriority));
        assertEquals(taskPriority, taskPriorityService.get(3L));
        verify(taskPriorityRepository, times(1)).findById(3L);
    }

    @Test
    public void create() {
        TaskPriority taskPriority = new TaskPriority(null, "High", 3, new ArrayList<>());

        taskPriorityService.create(taskPriority);
        verify(taskPriorityRepository, times(1)).save(taskPriority);
    }

    @Test
    public void refresh() {
        TaskPriority taskPriority = new TaskPriority(3L, "BUG", 2, new ArrayList<>());

        taskPriorityService.refresh(taskPriority);
        verify(taskPriorityRepository, times(1)).save(taskPriority);
    }

    @Test
    public void remove() {
        TaskPriority taskPriority = new TaskPriority(3L, "High", 3, new ArrayList<>());

        when(taskPriorityRepository.findById(3L)).thenReturn(Optional.of(taskPriority));

        taskPriorityService.remove(taskPriority);
        verify(taskPriorityRepository, times(1)).delete(taskPriority);
    }
}

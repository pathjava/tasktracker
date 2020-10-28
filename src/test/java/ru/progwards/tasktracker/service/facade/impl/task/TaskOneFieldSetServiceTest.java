package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.impl.task.TaskOneFieldSetService;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TaskOneFieldSetServiceTest {

    @Mock
    private TaskOneFieldSetService taskOneFieldSetService;

    @Test
    public void testSetOneField() {
        taskOneFieldSetService.setOneField(
                new UpdateOneValue(1L, "TT1-1", "code")
        );

        verify(taskOneFieldSetService, times(1)).setOneField(any(UpdateOneValue.class));
    }
}
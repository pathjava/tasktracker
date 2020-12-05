package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.UpdateOneFieldService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/**
 * Тестирование сервиса обновления поля задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskUpdateOneFieldServiceTest {

    private final UpdateOneValue oneValue;
    @Mock
    private UpdateOneFieldService<Task> updateOneFieldService;

    {
        oneValue = new UpdateOneValue(
                1L, "Test task 1 UpdateOneFieldService updated", "name"
        );
    }

    @Test
    public void updateOneField() {
        doNothing().when(updateOneFieldService).updateOneField(isA(UpdateOneValue.class));

        updateOneFieldService.updateOneField(oneValue);

        verify(updateOneFieldService, times(1)).updateOneField(oneValue);
    }
}
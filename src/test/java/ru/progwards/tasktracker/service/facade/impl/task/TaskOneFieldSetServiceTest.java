package ru.progwards.tasktracker.service.facade.impl.task;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.OneFieldSetService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * тестирование сервиса обновления поля задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
public class TaskOneFieldSetServiceTest {

    @Mock
    private OneFieldSetService<Task> service;

    @Test
    public void testSetOneField() {
        service.setOneField(
                new UpdateOneValue(1L, "TT1-1", "code")
        );

        verify(service, times(1)).setOneField(any(UpdateOneValue.class));
    }
}
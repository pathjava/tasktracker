package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * тестирование получения одной связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskGetServiceTest {

    @Mock
    private GetService<Long, RelatedTask> service;

    @Test
    void get() {
        when(service.get(anyLong())).thenReturn(
                new RelatedTask(1L, new RelationType(
                        1L, "блокирующая", new RelationType(2L, "блокируемая", null)),
                        1L, 2L)
        );

        RelatedTask relatedTask = service.get(1L);

        assertNotNull(relatedTask);

        assertEquals(1, relatedTask.getId());
    }
}
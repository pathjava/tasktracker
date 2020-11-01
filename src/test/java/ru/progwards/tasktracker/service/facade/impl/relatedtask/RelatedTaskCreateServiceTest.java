package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * тестирование сервиса создания связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskCreateServiceTest {

    @Mock
    private CreateService<RelatedTask> createService;

    @Test
    void testCreate() {
        createService.create(
                new RelatedTask(
                        1L, new RelationType(1L, "блокирующая", new RelationType(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );

        verify(createService, times(1)).create(any(RelatedTask.class));
    }
}
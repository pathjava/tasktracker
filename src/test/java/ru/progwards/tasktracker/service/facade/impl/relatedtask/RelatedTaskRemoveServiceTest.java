package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class RelatedTaskRemoveServiceTest {

    @Mock
    private RelatedTaskRemoveService removeService;

    @Test
    void testRemove() {
        removeService.remove(
                new RelatedTask(
                        1L, new RelationType(1L, "блокирующая", new RelationType(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );

        verify(removeService, times(1)).remove(any(RelatedTask.class));
    }
}
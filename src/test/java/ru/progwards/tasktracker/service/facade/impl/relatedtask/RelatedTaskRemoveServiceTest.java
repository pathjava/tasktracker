package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * тестирование сервиса удаления связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskRemoveServiceTest {

    @Autowired
    private RemoveService<RelatedTask> removeService;

    @Autowired
    private CreateService<RelatedTask> createService;

    @Autowired
    private GetService<Long, RelatedTask> getService;

    @Autowired
    private GetListByTaskService<Long, RelatedTask> listByTaskService;

    @BeforeEach
    void before() {
        createService.create(
                new RelatedTask(
                        null, new RelationType(1L, "блокирующая RemoveService", new RelationType(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );
    }

    @Test
    void remove() {
        Long id = listByTaskService.getListByTaskId(2L).stream()
                .filter(e -> e.getRelationType().getName().equals("блокирующая RemoveService")).findFirst()
                .map(RelatedTask::getId)
                .orElse(null);

        if (id != null) {
            removeService.remove(
                    new RelatedTask(
                            id, new RelationType(1L, "блокирующая", new RelationType(
                            2L, "блокируемая", null)),
                            1L, 2L)
            );

            RelatedTask task = getService.get(id);

            assertNull(task);
        } else
            fail();
    }
}
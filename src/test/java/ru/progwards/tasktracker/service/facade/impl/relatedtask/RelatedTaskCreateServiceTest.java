package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * тестирование сервиса создания связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskCreateServiceTest {

    @Autowired
    private CreateService<RelatedTask> createService;

    @Autowired
    private GetService<Long, RelatedTask> getService;

    @Autowired
    private RemoveService<RelatedTask> removeService;

    @Autowired
    private GetListByTaskService<Long, RelatedTask> listByTaskService;

    @Test
    void testCreate() {
        createService.create(
                new RelatedTask(
                        null, new RelationType(1L, "блокирующая CreateService", new RelationType(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );

        Long id = listByTaskService.getListByTaskId(2L).stream()
                .filter(e -> e.getRelationType().getName().equals("блокирующая CreateService")).findFirst()
                .map(RelatedTask::getId)
                .orElse(null);

        if (id != null) {
            RelatedTask task = getService.get(id);

            assertNotNull(task);

            assertThat(task.getRelationType().getName(), equalTo("блокирующая CreateService"));

            removeService.remove(task);
        } else
            fail();
    }
}
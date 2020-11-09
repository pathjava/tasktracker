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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование получения одной связанной задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskGetServiceTest {

    @Autowired
    private GetService<Long, RelatedTask> getService;

    @Autowired
    private RemoveService<RelatedTask> removeService;

    @Autowired
    private CreateService<RelatedTask> createService;

    @Autowired
    private GetListByTaskService<Long, RelatedTask> listByTaskService;

    @BeforeEach
    void before() {
        createService.create(
                new RelatedTask(
                        null, new RelationType(1L, "блокирующая GetService", new RelationType(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );
    }

    @Test
    void get() {
        Long id = listByTaskService.getListByTaskId(2L).stream()
                .filter(e -> e.getRelationType().getName().equals("блокирующая GetService")).findFirst()
                .map(RelatedTask::getId)
                .orElse(null);

        if (id != null) {
            RelatedTask task = getService.get(id);

            assertNotNull(task);

            assertThat(task.getRelationType().getName(), equalTo("блокирующая GetService"));

            removeService.remove(task);
        } else
            fail();
    }

    @Test
    void get_Return_Null(){
        RelatedTask task = getService.get(anyLong());

        assertNull(task);
    }
}
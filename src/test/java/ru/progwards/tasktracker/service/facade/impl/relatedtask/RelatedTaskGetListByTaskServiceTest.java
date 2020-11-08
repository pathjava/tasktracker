package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.vo.RelatedTask;
import ru.progwards.tasktracker.service.vo.RelationType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * тестирование сервиса получения коллекции связанных задач по идентификатору задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskGetListByTaskServiceTest {

    @Autowired
    private GetListByTaskService<Long, RelatedTask> listByTaskService;

    @Autowired
    private CreateService<RelatedTask> createService;

    @Autowired
    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    @BeforeEach
    void before() {
        jsonHandler.getMap().clear();

        createService.create(
                new RelatedTask(
                        null, new RelationType(1L, "блокирующая GetListByTaskService 1", new RelationType(
                        2L, "блокируемая", null)),
                        1L, 120L)
        );
        createService.create(
                new RelatedTask(
                        null, new RelationType(2L, "блокирующая GetListByTaskService 2", new RelationType(
                        2L, "блокируемая", null)),
                        3L, 120L)
        );
    }

    @Test
    void getListByTaskId() {
        Collection<RelatedTask> collection = listByTaskService.getListByTaskId(120L);

        assertNotNull(collection);

        List<String> list = collection.stream()
                .map(task -> task.getRelationType().getName())
                .collect(Collectors.toList());

        assertThat(list, containsInAnyOrder("блокирующая GetListByTaskService 1",
                "блокирующая GetListByTaskService 2"));


    }

    @Test
    public void getListByTaskId_Return_Empty_Collection() {
        jsonHandler.getMap().clear();

        Collection<RelatedTask> collection = listByTaskService.getListByTaskId(anyLong());

        assertTrue(collection.isEmpty());
    }
}
package ru.progwards.tasktracker.service.facade.impl.relatedtask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RelatedTaskGetListByTaskServiceTest {

    @Autowired
    private GetListByTaskService<Long, RelatedTask> listByTaskService;

    @Test
    void getListByTaskId() {
        Collection<RelatedTask> collection = listByTaskService.getListByTaskId(2L);

        assertNotNull(collection);

        List<String> list = collection.stream()
                .map(task -> task.getRelationType().getName())
                .collect(Collectors.toList());

        assertThat(list, containsInAnyOrder("блокируемая", "блокируемая", "блокирующая"));
    }
}
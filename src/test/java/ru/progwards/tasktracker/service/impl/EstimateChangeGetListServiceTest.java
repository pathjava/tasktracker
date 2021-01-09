package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.GetListService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тестирование получения списка вариантов изменения значения продолжительности выполнения задачи
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class EstimateChangeGetListServiceTest {

    @Qualifier("estimateChangeGetListService")
    @Autowired
    private GetListService<String> service;

    @Test
    void getList() {
        List<String> list = service.getList();

        assertNotNull(list);

        assertThat(list.size(), equalTo(5));

        assertThat(
                list, containsInAnyOrder(
                        "AUTO_REDUCE",
                        "DONT_CHANGE",
                        "SET_TO_VALUE",
                        "REDUCE_BY_VALUE",
                        "INCREASE_BY_VALUE"
                )
        );
    }
}
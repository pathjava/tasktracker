package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetListService;

import java.util.Collection;

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
        Collection<String> collection = service.getList();

        assertNotNull(collection);

        assertThat(collection.size(), equalTo(5));

        assertThat(
                collection, containsInAnyOrder(
                        "AUTO_REDUCE",
                        "DONT_CHANGE",
                        "SET_TO_VALUE",
                        "REDUCE_BY_VALUE",
                        "INCREASE_BY_VALUE"
                )
        );
    }
}
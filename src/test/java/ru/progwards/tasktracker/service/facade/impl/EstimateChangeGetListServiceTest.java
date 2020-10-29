package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.service.facade.GetListService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class EstimateChangeGetListServiceTest {

    @Qualifier("estimateChangeGetListService")
    @Mock
    private GetListService<String> getListService;

    @Test
    void getList() {
        when(getListService.getList()).thenReturn(
                Arrays.asList("AUTO_REDUCE", "DONT_CHANGE", "SET_TO_VALUE")
        );

        Collection<String> collection = getListService.getList();

        assertNotNull(collection);

        assertThat(collection, containsInAnyOrder("AUTO_REDUCE", "DONT_CHANGE", "SET_TO_VALUE"));
    }

    @Test
    void getList_return_Null() {
        when(getListService.getList()).thenReturn(Collections.emptyList());

        Collection<String> collection = getListService.getList();

        assertTrue(collection.isEmpty());
    }
}
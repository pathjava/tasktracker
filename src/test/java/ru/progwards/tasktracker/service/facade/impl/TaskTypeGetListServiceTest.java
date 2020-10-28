package ru.progwards.tasktracker.service.facade.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TaskTypeGetListServiceTest {

    @Autowired
    private TaskTypeGetListService getListService;

    @Test
    void getList() {
        Collection<String> collection = getListService.getList();

        assertNotNull(collection);

        assertThat(collection, containsInAnyOrder("BUG", "TASK", "EPIC"));
    }
}
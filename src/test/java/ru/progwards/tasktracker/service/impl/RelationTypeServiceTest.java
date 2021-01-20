package ru.progwards.tasktracker.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.model.RelationType;
import ru.progwards.tasktracker.service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static ru.progwards.tasktracker.objects.GetModel.getRelationType;

/**
 * Тестирование сервиса типа отношения связи между задачами
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelationTypeServiceTest {

    private final List<RelationType> relationTypes = new ArrayList<>();
    @Mock
    private GetService<Long, RelationType> getService;
    @Mock
    private CreateService<RelationType> createService;
    @Mock
    private RemoveService<RelationType> removeService;
    @Mock
    private RefreshService<RelationType> refreshService;
    @Mock
    private GetListService<RelationType> getListService;

    {
        for (int i = 0; i < 3; i++) {
            RelationType relationType = getRelationType();
            relationType.setId(1L + i);
            relationType.setName("type " + (1 + i));
            relationTypes.add(relationType);
        }
    }

    @Test
    void get() {
        when(getService.get(anyLong())).thenReturn(relationTypes.get(0));

        RelationType rt = getService.get(1L);

        assertNotNull(rt);

        verify(getService, times(1)).get(1L);

        assertThat(rt.getName(), is("type 1"));
    }

    @Test
    void get_Return_Null() {
        when(getService.get(anyLong())).thenReturn(null);

        RelationType rt = getService.get(1L);

        assertNull(rt);

        verify(getService, times(1)).get(1L);
    }

    @Test
    void getList() {
        when(getListService.getList()).thenReturn(relationTypes);

        List<RelationType> list = getListService.getList();

        assertThat(list.size(), equalTo(3));

        verify(getListService, times(1)).getList();

        assertThat(
                list.stream()
                        .map(RelationType::getName)
                        .collect(Collectors.toList()),
                containsInAnyOrder("type 1", "type 2", "type 3"));
    }

    @Test
    void getList_Return_Empty_Collection() {
        when(getListService.getList()).thenReturn(Collections.emptyList());

        List<RelationType> list = getListService.getList();

        assertTrue(list.isEmpty());

        verify(getListService, times(1)).getList();
    }

    @Test
    void create() {
        doNothing().when(createService).create(isA(RelationType.class));

        createService.create(relationTypes.get(0));

        verify(createService, times(1)).create(relationTypes.get(0));
    }

    @Test
    void refresh() {
        doNothing().when(refreshService).refresh(isA(RelationType.class));

        refreshService.refresh(relationTypes.get(0));

        verify(refreshService, times(1)).refresh(relationTypes.get(0));
    }

    @Test
    void remove() {
        doNothing().when(removeService).remove(isA(RelationType.class));

        removeService.remove(relationTypes.get(0));

        verify(removeService, times(1)).remove(relationTypes.get(0));
    }
}
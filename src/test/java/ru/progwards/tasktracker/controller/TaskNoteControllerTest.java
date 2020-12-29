package ru.progwards.tasktracker.controller;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import ru.progwards.tasktracker.dto.TaskNoteDtoFull;
import ru.progwards.tasktracker.dto.TaskNoteDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.TaskNote;
import ru.progwards.tasktracker.service.*;

import java.util.Collection;

@SpringBootTest
@AutoConfigureMockMvc
class TaskNoteControllerTest {
    @Mock
    private GetService<Long, TaskNote> getService;
    @Mock
    private RemoveService<TaskNote> removeService;
    @Mock
    private CreateService<TaskNote> createService;
    @Mock
    private RefreshService<TaskNote> refreshService;
    @Mock
    private Converter<TaskNote, TaskNoteDtoFull> dtoPreviewConverter;
    @Mock
    private Converter<TaskNote, TaskNoteDtoPreview> dtoFullConverter;
    @Mock
    private GetListByTaskService<Long, TaskNote> listByTaskService;

    @InjectMocks
    TaskNoteController tnc;

    @Test
    void getListTaskNotes() {
//        ResponseEntity<Collection<TaskNoteDtoFull>> re =  tnc.getListTaskNotes(1L);
    }

    @Test
    void createTaskNote() {
    }

    @Test
    void updateTaskNote() {
    }

    @Test
    void deleteTaskNote() {
    }
}
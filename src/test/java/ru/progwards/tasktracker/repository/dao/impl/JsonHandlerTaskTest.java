package ru.progwards.tasktracker.repository.dao.impl;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.progwards.tasktracker.MainForTest;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;

//@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
@ContextConfiguration(classes = MainForTest.class)
public class JsonHandlerTaskTest extends TestCase {

    @Autowired
    private JsonHandlerTask jsonHandlerTask;

    @Before
    public void clear(){
        jsonHandlerTask.tasks.clear();
    }

    @Test
    public void testWrite() {
        int sizeOne = jsonHandlerTask.tasks.size();
        jsonHandlerTask.tasks.put(1L, new TaskEntity(1L, "task1", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));
        jsonHandlerTask.tasks.put(2L, new TaskEntity(2L, "task2", "description2", TaskType.EPIC, Priority.CRITICAL,
                002L, 007L,
                ZonedDateTime.now().plusDays(1).toEpochSecond(), ZonedDateTime.now().plusDays(3).toEpochSecond(),
                101, 0006L, "STR_CODE_RRR", WorkflowStatus.IN_PROGRESS, "second_version",
                123456L, 123456L, 123456L));
        jsonHandlerTask.write();
        int sizeTwo = jsonHandlerTask.tasks.size();
        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }

    @Test
    public void testRead() {
        int sizeOne = jsonHandlerTask.tasks.size();
        jsonHandlerTask.read();
        int sizeTwo = jsonHandlerTask.tasks.size();
        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }
}
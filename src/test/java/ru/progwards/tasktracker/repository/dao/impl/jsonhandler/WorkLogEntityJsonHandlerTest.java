package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * тестирование обработчика Json логов
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class WorkLogEntityJsonHandlerTest {

    @Autowired
    private JsonHandler<Long, WorkLogEntity> jsonHandler;

    @BeforeEach
    public void clearBefore() {
        jsonHandler.getMap().clear();
    }

    @AfterEach
    public void clearAfter() {
        jsonHandler.getMap().clear();
    }

    @Test
    public void testWrite() {
        assertEquals(0, jsonHandler.getMap().size());

        createEntitiesForTest();

        jsonHandler.write();

        assertEquals(2, jsonHandler.getMap().size());
    }

    @Test
    public void testRead() {
        assertEquals(0, jsonHandler.getMap().size());
        createEntitiesForTest();
        jsonHandler.read();
        assertEquals(2, jsonHandler.getMap().size());
    }

    private void createEntitiesForTest() {
        jsonHandler.getMap().put(
                1L, new WorkLogEntity(null, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log 1", null, null)
        );
        jsonHandler.getMap().put(
                2L, new WorkLogEntity(null, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log 2", null, null)
        );
    }
}
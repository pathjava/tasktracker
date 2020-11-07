package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.WorkLogEntity;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void clear() {
        jsonHandler.getMap().clear();
    }

    @Test
    public void testWrite() {
        int sizeOne = jsonHandler.getMap().size();

        jsonHandler.getMap().put(
                1L, new WorkLogEntity(1L, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log 1", null, null)
        );
        jsonHandler.getMap().put(
                2L, new WorkLogEntity(2L, 2L, null, null, ZonedDateTime.now().toEpochSecond(),
                        "Description Log 2", null, null)
        );

        jsonHandler.write();

        int sizeTwo = jsonHandler.getMap().size();

        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }

    @Test
    public void testRead() {
        int sizeOne = jsonHandler.getMap().size();
        jsonHandler.read();
        int sizeTwo = jsonHandler.getMap().size();

        assertNotNull(jsonHandler.getMap());
        assertEquals(0, sizeOne);
        assertEquals(2, sizeTwo);
    }
}
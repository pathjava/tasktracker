package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * тестирование обработчика json связанных задач
 *
 * @author Oleg Kiselev
 */
@SpringBootTest
class RelatedTaskEntityJsonHandlerTest {

    @Autowired
    private JsonHandler<Long, RelatedTaskEntity> jsonHandler;

    @BeforeEach
    public void clearBefore() {
        jsonHandler.getMap().clear();
    }

    @AfterEach
    public void clearAfter() {
        jsonHandler.getMap().clear();
    }

    @Test
    void write() {
        assertEquals(0, jsonHandler.getMap().size());

        createEntitiesForTest();

        jsonHandler.write();

        assertEquals(2, jsonHandler.getMap().size());
    }

    @Test
    void read() {
        assertEquals(0, jsonHandler.getMap().size());
        createEntitiesForTest();
        jsonHandler.read();
        assertEquals(2, jsonHandler.getMap().size());
    }

    private void createEntitiesForTest() {
        jsonHandler.getMap().put(
                1L, new RelatedTaskEntity(
                        1L, new RelationTypeEntity(null, "блокирующая", null),
                        1L, null)
        );
        jsonHandler.getMap().put(
                2L, new RelatedTaskEntity(
                        2L, new RelationTypeEntity(null, "блокирующая", null),
                        2L, null)
        );
    }
}
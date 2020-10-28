package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.dao.JsonHandler;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RelationTypeEntityJsonHandlerTest {

    @Autowired
    private JsonHandler<Long, RelationTypeEntity> jsonHandler;

    @BeforeEach
    public void clear() {
        jsonHandler.getMap().clear();
    }

    @Test
    void write() {
        assertEquals(jsonHandler.getMap().size(), 0);

        jsonHandler.getMap().put(
                1L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(2L, "блокируемая", null))
        );
        jsonHandler.getMap().put(
                2L, new RelationTypeEntity(2L, "блокируемая", new RelationTypeEntity(1L, "блокирующая", null))
        );
        jsonHandler.getMap().put(
                3L, new RelationTypeEntity(3L, "ссылается", new RelationTypeEntity(3L, "ссылается", null))
        );

        assertEquals(jsonHandler.getMap().size(), 3);
    }

    @Test
    void read() {
        assertEquals(jsonHandler.getMap().size(), 0);
        jsonHandler.read();
        assertEquals(jsonHandler.getMap().size(), 3);
    }
}
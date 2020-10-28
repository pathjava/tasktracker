package ru.progwards.tasktracker.repository.dao.impl.jsonhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.progwards.tasktracker.repository.entity.RelatedTaskEntity;
import ru.progwards.tasktracker.repository.entity.RelationTypeEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RelatedTaskEntityJsonHandlerTest {

    @Autowired
    private RelatedTaskEntityJsonHandler jsonHandler;

    @BeforeEach
    public void clear() {
        jsonHandler.relatedTasks.clear();
    }

    @Test
    void write() {
        assertEquals(jsonHandler.relatedTasks.size(), 0);

        jsonHandler.relatedTasks.put(
                1L, new RelatedTaskEntity(
                        1L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(
                        2L, "блокируемая", null)),
                        1L, 2L)
        );
        jsonHandler.relatedTasks.put(
                2L, new RelatedTaskEntity(
                        2L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(
                        1L, "блокируемая", null)),
                        2L, 3L)
        );
        jsonHandler.relatedTasks.put(
                3L, new RelatedTaskEntity(
                        3L, new RelationTypeEntity(1L, "блокирующая", new RelationTypeEntity(
                        3L, "блокируемая", null)),
                        2L, 4L)
        );
        jsonHandler.relatedTasks.put(
                5L, new RelatedTaskEntity(
                        5L, new RelationTypeEntity(2L, "блокируемая", new RelationTypeEntity(
                        3L, "блокирующая", null)),
                        2L, 1L)
        );
        jsonHandler.relatedTasks.put(
                6L, new RelatedTaskEntity(
                        6L, new RelationTypeEntity(2L, "блокируемая", new RelationTypeEntity(
                        3L, "блокирующая", null)),
                        3L, 2L)
        );
        jsonHandler.relatedTasks.put(
                7L, new RelatedTaskEntity(
                        7L, new RelationTypeEntity(2L, "блокируемая", new RelationTypeEntity(
                        3L, "блокирующая", null)),
                        4L, 2L)
        );

        jsonHandler.write();

        assertEquals(jsonHandler.relatedTasks.size(), 6);
    }

    @Test
    void read() {
        assertEquals(jsonHandler.relatedTasks.size(), 0);
        jsonHandler.read();
        assertEquals(jsonHandler.relatedTasks.size(), 6);
    }
}
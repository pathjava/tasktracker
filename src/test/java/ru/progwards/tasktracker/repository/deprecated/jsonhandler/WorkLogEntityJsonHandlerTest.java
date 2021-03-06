//package ru.progwards.tasktracker.repository.deprecated.jsonhandler;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
//import ru.progwards.tasktracker.repository.deprecated.entity.WorkLogEntity;
//
//import java.time.ZonedDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
///**
// * тестирование обработчика Json логов
// *
// * @author Oleg Kiselev
// */
//@SpringBootTest
//@Deprecated
//class WorkLogEntityJsonHandlerTest {
//
//    @Autowired
//    private JsonHandler<Long, WorkLogEntity> jsonHandler;
//
//    @BeforeEach
//    public void clearBefore() {
//        jsonHandler.getMap().clear();
//    }
//
//    @AfterEach
//    public void clearAfter() {
//        jsonHandler.getMap().clear();
//    }
//
//    @Test
//    public void write() {
//        assertEquals(0, jsonHandler.getMap().size());
//
//        createEntitiesForTest();
//
//        jsonHandler.write();
//
//        assertEquals(2, jsonHandler.getMap().size());
//    }
//
//    @Test
//    public void read() {
//        assertEquals(0, jsonHandler.getMap().size());
//        createEntitiesForTest();
//        jsonHandler.read();
//        assertEquals(2, jsonHandler.getMap().size());
//    }
//
//    private void createEntitiesForTest() {
//        jsonHandler.getMap().put(
//                1L, new WorkLogEntity(null, null, null, null, ZonedDateTime.now().toEpochSecond(),
//                        "Description Log 1")
//        );
//        jsonHandler.getMap().put(
//                2L, new WorkLogEntity(null, null, null, null, ZonedDateTime.now().toEpochSecond(),
//                        "Description Log 2")
//        );
//    }
//}
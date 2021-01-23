//package ru.progwards.tasktracker.repository.deprecated.jsonhandler;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.progwards.tasktracker.repository.deprecated.JsonHandler;
//import ru.progwards.tasktracker.repository.deprecated.entity.TaskPriorityEntity;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@SpringBootTest
//public class TaskPriorityEntityJsonHandlerTest {
//
//    @Mock
//    private JsonHandler<Long, TaskPriorityEntity> taskPriorityEntityJsonHandler;
//
//    @BeforeEach
//    public void initMock() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    // проверка размеров getMap() до записи в файл и после чтения из файла
//    @Test
//    final void writeReadTest() {
//
//        Map<Long, TaskPriorityEntity> map = new ConcurrentHashMap<>();
//
//        for (long i = 0; i < 10; i++) {
//            map.put(i, new TaskPriorityEntity(i, "name"+i, (int)i));
//        }
//
//        Mockito.when(taskPriorityEntityJsonHandler.getMap()).thenReturn(map);
//
//        int beforeSize = taskPriorityEntityJsonHandler.getMap().size();
//        taskPriorityEntityJsonHandler.write();
//
//        taskPriorityEntityJsonHandler.read();
//        int afterSize = taskPriorityEntityJsonHandler.getMap().size();
//
//        Assertions.assertEquals(beforeSize, afterSize);
//    }
//}

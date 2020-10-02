package repository.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import repository.dao.impl.JsonHandlerImpl;
import repository.dao.impl.TaskRepository;
import repository.entity.Task;
import service.converter.impl.ConverterTask;
import service.vo.TaskModel;
import util.types.Priority;
import util.types.TaskType;
import util.types.WorkflowStatus;

import java.time.ZonedDateTime;

@SpringBootApplication
public class MainForTest {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainForTest.class, args);
        JsonHandlerImpl jsonHandler = context.getBean(JsonHandlerImpl.class);
        TaskRepository taskRepository = context.getBean(TaskRepository.class);
        ConverterTask converterTask = context.getBean(ConverterTask.class);

        jsonHandler.tasks.put(1L, new Task(1L, "task1", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));
        jsonHandler.tasks.put(2L, new Task(2L, "task2", "description2", TaskType.EPIC, Priority.CRITICAL,
                002L, 007L,
                ZonedDateTime.now().plusDays(1).toEpochSecond(), ZonedDateTime.now().plusDays(3).toEpochSecond(),
                101, 0006L, "STR_CODE_RRR", WorkflowStatus.IN_PROGRESS, "second_version",
                123456L, 123456L, 123456L));
        jsonHandler.write();

//        for (Task task : taskRepository.get()) {
//            System.out.println(task);
//        }

//        taskRepository.delete(1L);

        TaskModel taskModel = converterTask.convertTo(taskRepository.get(1L));
        System.out.println(taskModel);
    }
}

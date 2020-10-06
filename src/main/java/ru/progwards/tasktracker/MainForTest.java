package ru.progwards.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.progwards.tasktracker.repository.dao.impl.TaskRepository;
import ru.progwards.tasktracker.repository.dao.impl.JsonHandlerTask;
import ru.progwards.tasktracker.repository.entity.TaskEntity;
import ru.progwards.tasktracker.service.converter.impl.ConverterTask;
import ru.progwards.tasktracker.service.facade.impl.TaskOneFieldSetService;
import ru.progwards.tasktracker.service.facade.impl.TaskRemoveService;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;
import ru.progwards.tasktracker.util.types.Priority;
import ru.progwards.tasktracker.util.types.TaskType;
import ru.progwards.tasktracker.util.types.WorkflowStatus;

import java.time.ZonedDateTime;


@SpringBootApplication
public class MainForTest {
    public static void main(String[] args) {
//        SpringApplication.run(MainForTest.class, args);
        ApplicationContext context = SpringApplication.run(MainForTest.class, args);
        JsonHandlerTask jsonHandler = context.getBean(JsonHandlerTask.class);
        TaskRepository taskRepository = context.getBean(TaskRepository.class);
        ConverterTask converterTask = context.getBean(ConverterTask.class);
        TaskRemoveService taskRemoveService = context.getBean(TaskRemoveService.class);
        TaskOneFieldSetService fieldSetService = context.getBean(TaskOneFieldSetService.class);

        jsonHandler.tasks.put(1L, new TaskEntity(1L, "task1", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L,
                ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().plusDays(1).toEpochSecond(),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));
        jsonHandler.tasks.put(2L, new TaskEntity(2L, "task2", "description2", TaskType.EPIC, Priority.CRITICAL,
                002L, 007L,
                ZonedDateTime.now().plusDays(1).toEpochSecond(), ZonedDateTime.now().plusDays(3).toEpochSecond(),
                101, 0006L, "STR_CODE_RRR", WorkflowStatus.IN_PROGRESS, "second_version",
                123456L, 123456L, 123456L));
        jsonHandler.write();

//        for (Task task : taskRepository.get()) {
//            System.out.println(task);
//        }

//        taskRepository.delete(1L);

//        Task task = converterTask.convertTo(taskRepository.get(1L));
//        System.out.println(task);
//
//        taskRemoveService.remove(task);

        fieldSetService.setOneField(new UpdateOneValue(1L, "NEW_VALUE", "strCode"));
    }
}

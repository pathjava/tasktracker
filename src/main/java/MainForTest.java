import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import repository.dao.impl.JsonHandlerImpl;
import repository.entity.Task;
import util.types.Priority;
import util.types.TaskType;
import util.types.WorkflowStatus;

import java.time.ZonedDateTime;

@SpringBootApplication
public class MainForTest {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainForTest.class, args);
        JsonHandlerImpl jsonHandler = context.getBean(JsonHandlerImpl.class);
        jsonHandler.read();
        jsonHandler.tasks.put(1L, new Task(1L, "task1", "description1", TaskType.BUG, Priority.MAJOR,
                001L, 003L, ZonedDateTime.now(), ZonedDateTime.now().plusDays(1),
                100, 0005L, "STR_CODE_TTT", WorkflowStatus.NEW, "new_version",
                123456L, 123456L, 123456L));
        jsonHandler.write();
    }
}

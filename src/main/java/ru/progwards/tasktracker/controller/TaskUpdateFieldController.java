package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.service.facade.UpdateOneFieldService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

/**
 * контроллер для обработки запроса по обновлению одного поля задачи
 *
 * @author Oleg Kiselev
 */
@Controller
public class TaskUpdateFieldController {

    private UpdateOneFieldService<Task> taskUpdateOneFieldService;

    @Autowired
    public void setTaskUpdateOneFieldService(UpdateOneFieldService<Task> taskUpdateOneFieldService) {
        this.taskUpdateOneFieldService = taskUpdateOneFieldService;
    }

    /**
     * @param task_id идентификатор задачи
     * @param oneValue объект, содержащий идентификатор задачи, имя обновляемого поля и новое значение поля
     * @return статус
     */
    @PutMapping("/rest/project/{project_id}/tasks/{task_id}/field")
    public ResponseEntity<UpdateOneValue> updateOneField(
            @PathVariable Long task_id, @RequestBody UpdateOneValue oneValue
    ) {
        if (oneValue == null)
            throw new BadRequestException("Значение обновляемого поля отсутствует!");

        if (!task_id.equals(oneValue.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        taskUpdateOneFieldService.updateOneField(oneValue);

        return new ResponseEntity<>(HttpStatus.OK); //TODO - стоит ли тут что-то возвращать?
    }
}

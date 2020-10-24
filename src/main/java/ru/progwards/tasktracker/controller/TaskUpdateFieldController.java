package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.progwards.tasktracker.controller.exception.UpdateFieldNotExistException;
import ru.progwards.tasktracker.service.facade.impl.task.TaskOneFieldSetService;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

@Controller
public class TaskUpdateFieldController {

    private TaskOneFieldSetService taskOneFieldSetService;

    @Autowired
    public void setTaskOneFieldSetService(TaskOneFieldSetService taskOneFieldSetService) {
        this.taskOneFieldSetService = taskOneFieldSetService;
    }

    @PutMapping("/rest/project/{project_id}/tasks/{task_id}/field")
    public ResponseEntity<UpdateOneValue> updateOneField(@RequestBody UpdateOneValue oneValue) {
        if (oneValue == null)
            throw new UpdateFieldNotExistException();

        taskOneFieldSetService.setOneField(oneValue);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

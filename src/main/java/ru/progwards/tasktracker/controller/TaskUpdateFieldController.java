package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.progwards.tasktracker.controller.exception.NotExistException;
import ru.progwards.tasktracker.service.facade.OneFieldSetService;
import ru.progwards.tasktracker.service.vo.Task;
import ru.progwards.tasktracker.service.vo.UpdateOneValue;

@Controller
public class TaskUpdateFieldController {

    private OneFieldSetService<Task> taskOneFieldSetService;

    @Autowired
    public void setTaskOneFieldSetService(OneFieldSetService<Task> taskOneFieldSetService) {
        this.taskOneFieldSetService = taskOneFieldSetService;
    }

    @PutMapping("/rest/project/{project_id}/tasks/{task_id}/field")
    public ResponseEntity<UpdateOneValue> updateOneField(@RequestBody UpdateOneValue oneValue) {
        if (oneValue == null)
            throw new NotExistException("Значение обновляемого поля отсутствует!");

        taskOneFieldSetService.setOneField(oneValue);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

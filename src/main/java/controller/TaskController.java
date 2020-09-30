package controller;

import controller.converter.Converter;
import controller.dto.TaskDto;
import service.api.TaskService;
import service.vo.TaskModel;

public class TaskController {

    private TaskService service;
    private Converter<TaskModel, TaskDto> converter;

    public void createTask(TaskDto dto){
        service.createTask(converter.toModel(dto));
    }

}

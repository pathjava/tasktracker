package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskAttachmentDto;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.TaskAttachment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Обработка запросов API по работе со связкой задача-вложение внутри задачи
 *
 * @author Gregory Lobkov
 */
@RestController
@RequestMapping("/rest/task/{task_id}/attachments")
public class AttachmentsController {

    @Autowired
    CreateService<TaskAttachment> createService;
    @Autowired
    RemoveService<TaskAttachment> removeService;
    @Autowired
    GetService<Long, TaskAttachment> getService;
    @Autowired
    GetListByTaskService<Long, TaskAttachment> getListByTaskService;
    @Autowired
    Converter<TaskAttachment, TaskAttachmentDto> dtoConverter;


    /**
     * Получить список связей задача-вложение по задаче
     * GET /rest/task/{task_id}/attachments
     *
     * @return список вложений
     */
    @GetMapping("")
    public ResponseEntity<Collection<TaskAttachmentDto>> getList(@PathVariable("task_id") Long task_id) {
        if(task_id == null)
            throw new BadRequestException("Task_id is not set");

        // получили список бизнес-объектов
        Collection<TaskAttachment> listByTaskId = getListByTaskService.getListByTaskId(task_id);
        List<TaskAttachmentDto> resultList = new ArrayList<>(listByTaskId.size());
        // преобразуем к dto
        for (TaskAttachment entity:listByTaskId) {
            TaskAttachmentDto dto = dtoConverter.toDto(entity);
            resultList.add(dto);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    /**
     * Создаём вложения
     * POST /rest/task/{task_id}/attachments/add
     *
     * @param newEntities передаем список связей задача-вложение
     * @return возвращаем список связей задача-вложение
     */
    @PostMapping("/add")
    public ResponseEntity<Collection<TaskAttachmentDto>> createList(@PathVariable("task_id") Long task_id,
                                                                    @RequestBody Collection<TaskAttachmentDto> newEntities) {
        if (task_id == null)
            throw new BadRequestException("Task_id is not set");
        if (newEntities == null)
            throw new BadRequestException("TaskAttachment is null");
        if (newEntities.size() == 0)
            throw new BadRequestException("TaskAttachment is empty");

        List<TaskAttachmentDto> resultList = new ArrayList<>(newEntities.size());
        for(TaskAttachmentDto entity: newEntities) {
            entity.setTaskId(task_id);
            TaskAttachment vo = dtoConverter.toModel(entity);
            createService.create(vo);
            resultList.add(dtoConverter.toDto(vo));
        }

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    /**
     * Удалить существующие связи задача-вложение
     * POST /rest/task/{task_id}/attachments/delete
     *
     * @param ids идентификаторы удаляемых объектов
     */
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteList(@PathVariable("task_id") Long task_id,
                           @RequestBody Collection<Long> ids) {
        if (task_id == null)
            throw new BadRequestException("Task_id is not set");

        for (Long entity : ids) {
            TaskAttachment vo = getService.get(entity);
            if(vo.getTaskId() == task_id)
                removeService.remove(vo);
        }
    }

}

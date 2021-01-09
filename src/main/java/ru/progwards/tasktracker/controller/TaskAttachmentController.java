package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.TaskAttachmentDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;

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
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
public class TaskAttachmentController {

    private final CreateService<TaskAttachment> createService;
    private final RemoveService<TaskAttachment> removeService;
    private final GetService<Long, Task> getTaskService;
    private final GetService<Long, TaskAttachment> getService;
    private final Converter<TaskAttachment, TaskAttachmentDtoFull> dtoConverter;


    /**
     * Получить список связей задача-вложение по задаче
     * GET /rest/task/{task_id}/attachments
     *
     * @return список вложений
     */
    @GetMapping("")
    public ResponseEntity<Collection<TaskAttachmentDtoFull>> getList(@PathVariable("task_id") Long task_id) {
        if(task_id == null)
            throw new BadRequestException("Task_id is not set");

        // получили список бизнес-объектов
        Task task = getTaskService.get(task_id);
        Collection<TaskAttachment> listByTaskId = task.getAttachments();
        List<TaskAttachmentDtoFull> resultList = new ArrayList<>(listByTaskId.size());
        // преобразуем к dto
        for (TaskAttachment entity:listByTaskId) {
            TaskAttachmentDtoFull dto = dtoConverter.toDto(entity);
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
    public ResponseEntity<Collection<TaskAttachmentDtoFull>> createList(@PathVariable("task_id") Long task_id,
                                                                        @RequestBody Collection<TaskAttachmentDtoFull> newEntities) {
        if (task_id == null)
            throw new BadRequestException("Task_id is not set");
        if (newEntities == null)
            throw new BadRequestException("TaskAttachment is null");
        if (newEntities.size() == 0)
            throw new BadRequestException("TaskAttachment is empty");

        List<TaskAttachmentDtoFull> resultList = new ArrayList<>(newEntities.size());
        for(TaskAttachmentDtoFull entity: newEntities) {
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
//        if (task_id == null)
//            throw new BadRequestException("Task_id is not set");
//
//        for (Long entity : ids) {
//            TaskAttachment vo = getService.get(entity);
//            if(vo.getTask() == task_id)
//                removeService.remove(vo);
//        }
    }

}

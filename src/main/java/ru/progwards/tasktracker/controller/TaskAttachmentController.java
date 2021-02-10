package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.TaskAttachmentDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.TaskAttachment;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
@Validated
public class TaskAttachmentController {

    private final CreateService<TaskAttachment> createService;
    private final RemoveService<TaskAttachment> removeService;
    private final GetService<Long, Task> taskGetService;
    private final GetService<Long, TaskAttachment> getService;
    private final Converter<TaskAttachment, TaskAttachmentDtoFull> dtoConverter;


    /**
     * Получить список связей задача-вложение по задаче
     * GET /rest/task/{task_id}/attachments
     *
     * @return список вложений
     */
    @GetMapping(value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TaskAttachmentDtoFull>> getList(
            @PathVariable("task_id") @Positive Long task_id
    ) {
        if (task_id == null)
            throw new BadRequestException("Task_id is not set");

        // получили список бизнес-объектов
        Task task = taskGetService.get(task_id);
        Collection<TaskAttachment> listByTaskId = task.getAttachments();
        List<TaskAttachmentDtoFull> resultList = new ArrayList<>(listByTaskId.size());
        // преобразуем к dto
        for (TaskAttachment entity : listByTaskId) {
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
    public ResponseEntity<Collection<TaskAttachmentDtoFull>> createList(
            @PathVariable("task_id") @Positive Long task_id,
            @RequestBody @NotNull @Validated(Update.class) Collection<TaskAttachmentDtoFull> newEntities
    ) {
        List<TaskAttachmentDtoFull> resultList = new ArrayList<>(newEntities.size());
        Task task = taskGetService.get(task_id);
        for (TaskAttachmentDtoFull entity : newEntities) {
            TaskAttachment vo = dtoConverter.toModel(entity);
            vo.setTask(task);
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
    public void deleteList(
            @PathVariable("task_id") @Positive Long task_id,
            @RequestBody @NotNull @Positive Collection<Long> ids
    ) {
        for (Long id : ids)
            if (id != null) {
                TaskAttachment vo = getService.get(id);
                if (vo.getTask().getId().equals(task_id))
                    removeService.remove(vo);
            }
    }

}
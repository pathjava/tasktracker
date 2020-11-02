package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.RelatedTaskDto;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.CreateService;
import ru.progwards.tasktracker.service.facade.GetListByTaskService;
import ru.progwards.tasktracker.service.facade.GetService;
import ru.progwards.tasktracker.service.facade.RemoveService;
import ru.progwards.tasktracker.service.vo.RelatedTask;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * контроллер для работы со связанными задачами
 *
 * @author Oleg Kiselev
 */
@RestController
public class RelatedTaskController {

    private RemoveService<RelatedTask> removeService;
    private GetListByTaskService<Long, RelatedTask> listByTaskService;
    private CreateService<RelatedTask> createService;
    private Converter<RelatedTask, RelatedTaskDto> converter;
    private GetService<Long, RelatedTask> getService;

    @Autowired
    public void setRemoveService(
            RemoveService<RelatedTask> removeService,
            GetListByTaskService<Long, RelatedTask> listByTaskService,
            CreateService<RelatedTask> createService,
            Converter<RelatedTask, RelatedTaskDto> converter,
            GetService<Long, RelatedTask> getService
    ) {
        this.removeService = removeService;
        this.listByTaskService = listByTaskService;
        this.createService = createService;
        this.converter = converter;
        this.getService = getService;
    }

    /**
     * метод создания связанной задачи
     *
     * @param taskDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданную задачу
     */
    @PostMapping("/rest/relatedtask/create")
    public ResponseEntity<RelatedTaskDto> addRelatedTask(@RequestBody RelatedTaskDto taskDto) {
        if (taskDto == null)
            throw new BadRequestException("Пустой объект!");

        RelatedTask relatedTask = converter.toModel(taskDto);
        createService.create(relatedTask);
        RelatedTaskDto createdRelatedTask = converter.toDto(relatedTask);

        return new ResponseEntity<>(createdRelatedTask, HttpStatus.CREATED);
    }

    /**
     * @param task_id идентификатор задачи для которой надо получить связанные задачи
     * @return коллекция связанных задач
     */
    @GetMapping("/rest/relatedtask/{task_id}/list")
    public ResponseEntity<Collection<RelatedTaskDto>> getAllRelatedTasks(@PathVariable Long task_id) {
        if (task_id == null)
            throw new BadRequestException("Id: " + task_id + " не задан или задан неверно!");

        Collection<RelatedTaskDto> collection = listByTaskService.getListByTaskId(task_id).stream()
                .map(relatedTask -> converter.toDto(relatedTask))
                .collect(Collectors.toList());

        if (collection.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список задач пустой!");

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    /**
     * @param task_id идентификатор удаляемой задачи
     * @return статус
     */
    @DeleteMapping("/rest/relatedtask/{task_id}/delete")
    public ResponseEntity<RelatedTaskDto> deleteRelatedTask(@PathVariable Long task_id) {
        if (task_id == null)
            throw new BadRequestException("Id: " + task_id + " не задан или задан неверно!");

        RelatedTask relatedTask = getService.get(task_id);

        if (relatedTask != null)
            removeService.remove(relatedTask);
        else
            throw new NotFoundException("Связанная задача с id: " + task_id + " не найдена!");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.RelatedTaskDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetListByTaskService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;
import ru.progwards.tasktracker.model.RelatedTask;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер для работы со связанными задачами
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest/relatedtask",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RelatedTaskController {

    @Autowired
    private RemoveService<RelatedTask> removeService;
    @Autowired
    private GetListByTaskService<Long, RelatedTask> listByTaskService;
    @Autowired
    private CreateService<RelatedTask> createService;
    @Autowired
    private Converter<RelatedTask, RelatedTaskDtoFull> converter;
    @Autowired
    private GetService<Long, RelatedTask> getService;

    /**
     * Метод создания связанной задачи
     *
     * @param taskDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданную задачу
     */
    @PostMapping(value = "/create")
    public ResponseEntity<RelatedTaskDtoFull> createRelatedTask(@RequestBody RelatedTaskDtoFull taskDto) {
        if (taskDto == null)
            throw new BadRequestException("Пустой объект!");

        RelatedTask relatedTask = converter.toModel(taskDto);
        createService.create(relatedTask);
        RelatedTaskDtoFull createdRelatedTask = converter.toDto(relatedTask);

        return new ResponseEntity<>(createdRelatedTask, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции связанных задач по идентификатору задачи
     *
     * @param id идентификатор задачи для которой надо получить связанные задачи
     * @return коллекция связанных задач
     */
    @GetMapping(value = "/{id}/list")
    public ResponseEntity<Collection<RelatedTaskDtoFull>> getListRelatedTasks(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Collection<RelatedTaskDtoFull> collection = listByTaskService.getListByTaskId(id).stream()
                .map(relatedTask -> converter.toDto(relatedTask))
                .collect(Collectors.toList());

        if (collection.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список задач пустой!");

        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    /**
     * Метод удаления связанной задачи
     *
     * @param id идентификатор удаляемой задачи
     * @return статус
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<RelatedTaskDtoFull> deleteRelatedTask(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        RelatedTask relatedTask = getService.get(id);
        if (relatedTask != null)
            removeService.remove(relatedTask);
        else
            throw new NotFoundException("Связанная задача с id: " + id + " не найдена!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

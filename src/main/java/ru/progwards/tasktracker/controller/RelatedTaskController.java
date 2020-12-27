package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.RelatedTaskDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы со связанными задачами (RelatedTask)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest/relatedtask",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RelatedTaskController {

    private final @NonNull CreateService<RelatedTask> relatedTaskCreateService;
    private final @NonNull GetService<Long, RelatedTask> relatedTaskGetService;
    private final @NonNull RemoveService<RelatedTask> relatedTaskRemoveService;
    private final @NonNull GetService<Long, Task> taskGetService;
    private final @NonNull Converter<RelatedTask, RelatedTaskDtoFull> converter;

    /**
     * Метод создания связанной задачи (RelatedTask)
     *
     * @param relatedTaskDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданную RelatedTaskDtoFull
     */
    @PostMapping(value = "/create")
    public ResponseEntity<RelatedTaskDtoFull> create(@RequestBody RelatedTaskDtoFull relatedTaskDto) {
        if (relatedTaskDto == null)
            throw new BadRequestException("RelatedTaskDtoFull == null");

        RelatedTask relatedTask = converter.toModel(relatedTaskDto);
        relatedTaskCreateService.create(relatedTask);
        RelatedTaskDtoFull createdRelatedTask = converter.toDto(relatedTask);

        return new ResponseEntity<>(createdRelatedTask, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции связанных задач (RelatedTask) по идентификатору задачи (Task)
     *
     * @param id идентификатор задачи для которой надо получить связанные задачи
     * @return лист RelatedTaskDtoFull
     */
    @GetMapping(value = "/{id}/list")
    public ResponseEntity<List<RelatedTaskDtoFull>> getList(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Task task = taskGetService.get(id);
        List<RelatedTaskDtoFull> list = task.getRelatedTasks().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список RelatedTaskDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод удаления связанной задачи (RelatedTask)
     *
     * @param id идентификатор удаляемой задачи
     * @return статус
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<RelatedTaskDtoFull> delete(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        RelatedTask relatedTask = relatedTaskGetService.get(id);
        relatedTaskRemoveService.remove(relatedTask);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.RelatedTaskDtoFull;
import ru.progwards.tasktracker.dto.RelatedTaskDtoPreview;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetListService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RemoveService;
import ru.progwards.tasktracker.util.validator.validationstage.Create;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы со связанными задачами (RelatedTask)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest/relatedtask")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class RelatedTaskController {

    private final CreateService<RelatedTask> relatedTaskCreateService;
    private final GetService<Long, RelatedTask> relatedTaskGetService;
    private final RemoveService<RelatedTask> relatedTaskRemoveService;
    private final GetListService<RelatedTask> relatedTaskGetListService;
    private final GetService<Long, Task> taskGetService;
    private final Converter<RelatedTask, RelatedTaskDtoFull> relatedTaskDtoFullConverter;
    private final Converter<RelatedTask, RelatedTaskDtoPreview> relatedTaskDtoPreviewConverter;

    /**
     * Метод создания связанной задачи (RelatedTask)
     *
     * @param dtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданную RelatedTaskDtoFull
     */
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelatedTaskDtoFull> create(
            @Validated(Create.class) @RequestBody RelatedTaskDtoFull dtoFull) {

        RelatedTask relatedTask = relatedTaskDtoFullConverter.toModel(dtoFull);
        relatedTaskCreateService.create(relatedTask);
        RelatedTaskDtoFull createdRelatedTask = relatedTaskDtoFullConverter.toDto(relatedTask);

        return new ResponseEntity<>(createdRelatedTask, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции связанных задач (RelatedTask) по идентификатору задачи (Task)
     *
     * @param id идентификатор задачи для которой надо получить связанные задачи
     * @return лист RelatedTaskDtoFull
     */
    @GetMapping(value = "/{id}/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelatedTaskDtoPreview>> getListByTask(
            @PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {

        Task task = taskGetService.get(id);
        List<RelatedTaskDtoPreview> list = task.getRelatedTasks().stream()
                .map(relatedTaskDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список RelatedTaskDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения связи задачи (RelatedTask)
     *
     * @param id идентификатор связи задачи
     * @return возвращает RelatedTaskDtoFull
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelatedTaskDtoFull> get(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {

        RelatedTaskDtoFull relatedTaskDtoFull = relatedTaskDtoFullConverter.toDto(relatedTaskGetService.get(id));

        return new ResponseEntity<>(relatedTaskDtoFull, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции всех связанных задач (RelatedTask)
     *
     * @return лист RelatedTaskDtoFull
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelatedTaskDtoPreview>> getList() {
        List<RelatedTaskDtoPreview> list = relatedTaskGetListService.getList().stream()
                .map(relatedTaskDtoPreviewConverter::toDto)
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
    public ResponseEntity<RelatedTaskDtoFull> delete(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {

        RelatedTask relatedTask = relatedTaskGetService.get(id);
        relatedTaskRemoveService.remove(relatedTask);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

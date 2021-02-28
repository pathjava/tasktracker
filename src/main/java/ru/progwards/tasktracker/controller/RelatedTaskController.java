package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.RelatedTaskDtoFull;
import ru.progwards.tasktracker.dto.RelatedTaskDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.RelatedTask;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.validationstage.Create;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы со связями задач (RelatedTask)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest/relatedTask")
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
    private final Paging<Long, RelatedTask> relatedTaskPaging;
    private final Sorting<Long, RelatedTask> relatedTaskSorting;

    /**
     * Метод создания связи задачи (RelatedTask)
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
     * Метод получения связи задачи (RelatedTask)
     *
     * @param id идентификатор связи задачи
     * @return возвращает RelatedTaskDtoFull
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelatedTaskDtoFull> get(@PathVariable @Positive Long id) {

        RelatedTaskDtoFull relatedTaskDtoFull = relatedTaskDtoFullConverter.toDto(relatedTaskGetService.get(id));

        return new ResponseEntity<>(relatedTaskDtoFull, HttpStatus.OK);
    }

    /**
     * Метод получения листа связей задач (RelatedTask) по идентификатору задачи (Task)
     *
     * @param id идентификатор задачи для которой надо получить связанные задачи
     * @return лист RelatedTaskDtoFull
     */
    @GetMapping(value = "/{id}/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelatedTaskDtoPreview>> getListByTask(@PathVariable @Positive Long id) {

        Task task = taskGetService.get(id);
        List<RelatedTaskDtoPreview> list = task.getRelatedTasks().stream()
                .map(relatedTaskDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        checkListNotEmpty(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод проверки, что полученный лист не пустой
     *
     * @param list лист связей задач RelatedTaskDtoPreview
     */
    private void checkListNotEmpty(List<RelatedTaskDtoPreview> list) {
        if (list.isEmpty())
            throw new NotFoundException("List RelatedTaskDtoPreview is empty!");
    }

    /**
     * Метод получения сортированного листа связей задач (RelatedTask) по идентификатору задачи (Task)
     *
     * @param id   идентификатор задачи для которой надо получить связанные задачи
     * @param sort параметр/параметры, по которым происходит сортировка
     * @return сортированный лист RelatedTaskDtoFull
     */
    @GetMapping(value = "/{id}/listSort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelatedTaskDtoPreview>> getListSortByTask(@PathVariable @Positive Long id,
                                                                         Sort sort) {

        List<RelatedTaskDtoPreview> list = relatedTaskSorting.getSortListById(id, sort).stream()
                .map(relatedTaskDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        checkListNotEmpty(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения листа пагинации связей задач (RelatedTask) по идентификатору задачи (Task)
     *
     * @param id       идентификатор задачи для которой надо получить связанные задачи
     * @param pageable параметр/параметры по которым происходит выборка страницы пагинации объектов
     * @return лист пагинации RelatedTaskDtoFull
     */
    @GetMapping(value = "/{id}/listPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelatedTaskDtoPreview>> getListPageByTask(@PathVariable @Positive Long id,
                                                                         Pageable pageable) {

        List<RelatedTaskDtoPreview> list = relatedTaskPaging.getPageableListById(id, pageable).stream()
                .map(relatedTaskDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        checkListNotEmpty(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения листа всех связей задач (RelatedTask)
     *
     * @return лист RelatedTaskDtoFull
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelatedTaskDtoPreview>> getList() {
        List<RelatedTaskDtoPreview> list = relatedTaskGetListService.getList().stream()
                .map(relatedTaskDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        checkListNotEmpty(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения сортированного листа всех связей задач (RelatedTask)
     *
     * @param sort параметр/параметры, по которым происходит сортировка
     * @return сортированный лист RelatedTaskDtoFull
     */
    @GetMapping(value = "/listSort", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelatedTaskDtoPreview>> getListSort(Sort sort) {
        List<RelatedTaskDtoPreview> list = relatedTaskSorting.getSortList(sort).stream()
                .map(relatedTaskDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        checkListNotEmpty(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения листа пагинации всех связей задач (RelatedTask)
     *
     * @param pageable параметр/параметры по которым происходит выборка страницы пагинации объектов
     * @return лист пагинации RelatedTaskDtoFull
     */
    @GetMapping(value = "/listPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelatedTaskDtoPreview>> getListPage(Pageable pageable) {
        List<RelatedTaskDtoPreview> list = relatedTaskPaging.getPageableList(pageable).stream()
                .map(relatedTaskDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        checkListNotEmpty(list);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод удаления связанной задачи (RelatedTask)
     *
     * @param id идентификатор удаляемой задачи
     * @return статус
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<RelatedTaskDtoFull> delete(@PathVariable @Positive Long id) {

        RelatedTask relatedTask = relatedTaskGetService.get(id);
        relatedTaskRemoveService.remove(relatedTask);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

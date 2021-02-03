package ru.progwards.tasktracker.controller;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.TaskPriorityDtoFull;
import ru.progwards.tasktracker.dto.TaskPriorityDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.exception.OperationIsNotPossibleException;
import ru.progwards.tasktracker.model.TaskPriority;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер TaskPriority
 * @author Pavel Khovaylo
 */
@Validated
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/rest/task-priority/")
public class TaskPriorityController {
    /**
     * конвертер TaskPriority <-> TaskPriorityDtoFull
     */
    Converter<TaskPriority, TaskPriorityDtoFull> converterFull;
    /**
     * конвертер TaskPriority <-> TaskPriorityDtoPreview
     */
    Converter<TaskPriority, TaskPriorityDtoPreview> converterPreview;
    /**
     * сервисный класс для получение списка TaskPriority
     */
    GetService<Long, TaskPriority> taskPriorityGetService;
    /**
     * сервисный класс для получение TaskPriority
     */
    GetListService<TaskPriority> taskPriorityGetListService;
    /**
     * сервисный класс для создания TaskPriority
     */
    CreateService<TaskPriority> taskPriorityCreateService;
    /**
     * сервисный класс для обновления TaskPriority
     */
    RefreshService<TaskPriority> taskPriorityRefreshService;
    /**
     * сервисный класс для удаления TaskPriority
     */
    RemoveService<TaskPriority> taskPriorityRemoveService;

    /**
     * по запросу получаем список TaskPriorityDtoPreview
     * @return список TaskPriorityDto
     */
    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TaskPriorityDtoPreview>> get() {
        Collection<TaskPriorityDtoPreview> taskPriorityDtoPreviews =
                taskPriorityGetListService.getList().stream().
                        map(converterPreview::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(taskPriorityDtoPreviews, HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный TaskPriorityDtoFull; если такового нет, то бросаем исключение NotFoundException
     * id должен находится в диапазоне от 0 до 9_223_372_036_854_775_807 и не равен null
     * @param id идентификатор TaskPriorityDtoFull
     * @return TaskPriorityDto
     */
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskPriorityDtoFull> get(@NotNull @Min(0) @PathVariable("id") Long id) {
        TaskPriority taskPriority = taskPriorityGetService.get(id);
        if (taskPriority == null)
            throw new NotFoundException("Not found a taskPriority with id=" + id);

        return new ResponseEntity<>(converterFull.toDto(taskPriority), HttpStatus.OK);
    }

    /**
     * по запросу создаём TaskPriorityDtoFull
     * формируем объект TaskPriorityDtoFull, исходя из ограничений, относящихся к group = Create.class
     * @param taskPriorityDtoFull передаем наполненный TaskPriorityDto
     * @return созданный TaskPriorityDto
     */
    @PostMapping(value = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskPriorityDtoFull> create(@Validated(Create.class) @NotNull @RequestBody
                                                                  TaskPriorityDtoFull taskPriorityDtoFull) {
        TaskPriority taskPriority = converterFull.toModel(taskPriorityDtoFull);
        taskPriorityCreateService.create(taskPriority);
        TaskPriorityDtoFull createdTaskPriority = converterFull.toDto(taskPriority);

        return new ResponseEntity<>(createdTaskPriority, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий TaskPriorityDtoFull
     * id должен находится в диапазоне от 0 до 9_223_372_036_854_775_807 и не равен null
     * формируем объект TaskPriorityDtoFull, исходя из ограничений, относящихся к group = Update.class
     * @param id идентификатор изменяемого TaskPriorityDtoFull
     * @param taskPriorityDtoFull измененный TaskPriorityDtoFull
     */
    @PostMapping(value = "{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@NotNull @Min(0) @PathVariable("id") Long id,
                       @Validated(Update.class) @NotNull @RequestBody TaskPriorityDtoFull taskPriorityDtoFull) {

        if (!id.equals(taskPriorityDtoFull.getId()))
            throw new OperationIsNotPossibleException("Impossible to update task-priority");

        taskPriorityRefreshService.refresh(converterFull.toModel(taskPriorityDtoFull));
    }

    /**
     * по запросу удаляем нужный TaskPriority; если такого TaskPriority не существует, то бросаем исключение
     * NotFoundException
     * id должен находится в диапазоне от 0 до 9_223_372_036_854_775_807 и не равен null
     * @param id идентификатор удаляемого TaskPriorityDto
     */
    @PostMapping(value = "{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@NotNull @Min(0) @PathVariable("id") Long id) {
        TaskPriority taskPriority = taskPriorityGetService.get(id);
        if (taskPriority == null)
            throw new NotFoundException("Not found a taskPriority with id=" + id);

        taskPriorityRemoveService.remove(taskPriority);
    }
}
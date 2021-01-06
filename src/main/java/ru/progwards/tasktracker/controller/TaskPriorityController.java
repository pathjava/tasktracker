package ru.progwards.tasktracker.controller;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskPriorityDtoFull;
import ru.progwards.tasktracker.dto.TaskPriorityDtoPreview;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.TaskPriority;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллеры TaskPriority
 * @author Pavel Khovaylo
 */
@RequiredArgsConstructor(onConstructor_={@Autowired, @NonNull})
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping(value = "/rest/task-priority/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping("list")
    public ResponseEntity<Collection<TaskPriorityDtoPreview>> get() {
        Collection<TaskPriorityDtoPreview> taskPriorityDtoPreviews =
                taskPriorityGetListService.getList().stream().
                        map(converterPreview::toDto).collect(Collectors.toList());

        return new ResponseEntity<>(taskPriorityDtoPreviews, HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный TaskPriorityDtoFull; если такового нет, то бросаем исключение NotFoundException
     * @param id идентификатор TaskPriorityDtoFull
     * @return TaskPriorityDto
     */
    @GetMapping("{id}")
    public ResponseEntity<TaskPriorityDtoFull> get(@NotNull @Positive @PathVariable("id") Long id) {
        TaskPriority taskPriority = taskPriorityGetService.get(id);
        if (taskPriority == null)
            throw new NotFoundException("Not found a taskPriority with id=" + id);

        return new ResponseEntity<>(converterFull.toDto(taskPriority), HttpStatus.OK);
    }

    /**
     * по запросу создаём TaskPriorityDtoFull
     * @param taskPriorityDtoFull передаем наполненный TaskPriorityDto
     * @return созданный TaskPriorityDto
     */
    @Transactional
    @PostMapping("create")
    public ResponseEntity<TaskPriorityDtoFull> create(@Valid @RequestBody TaskPriorityDtoFull taskPriorityDtoFull) {
        if (taskPriorityDtoFull == null)
            throw new BadRequestException("Project is null");

        TaskPriority taskPriority = converterFull.toModel(taskPriorityDtoFull);
        taskPriorityCreateService.create(taskPriority);
        TaskPriorityDtoFull createdTaskPriority = converterFull.toDto(taskPriority);

        return new ResponseEntity<>(createdTaskPriority, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий TaskPriorityDtoFull
     * @param id идентификатор изменяемого TaskPriorityDtoFull
     * @param taskPriorityDtoFull измененный TaskPriorityDtoFull
     */
    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@NotNull @Positive @PathVariable("id") Long id,
                       @Valid @RequestBody TaskPriorityDtoFull taskPriorityDtoFull) {
        if (id == null)
            throw new BadRequestException("Id is null");

        if (taskPriorityDtoFull == null)
            throw new BadRequestException("Project is null");

        taskPriorityDtoFull.setId(id);
        taskPriorityRefreshService.refresh(converterFull.toModel(taskPriorityDtoFull));
    }

    /**
     * по запросу удаляем нужный TaskPriority; если такого TaskPriority не существует, то бросаем исключение
     * NotFoundException
     * @param id идентификатор удаляемого TaskPriorityDto
     */
    @PostMapping("{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@NotNull @Positive @PathVariable("id") Long id) {
        TaskPriority taskPriority = taskPriorityGetService.get(id);
        if (taskPriority == null)
            throw new NotFoundException("Not found a taskPriority with id=" + id);

        taskPriorityRemoveService.remove(taskPriority);
    }
}
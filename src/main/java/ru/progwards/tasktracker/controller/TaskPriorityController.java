package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.TaskPriorityDtoFull;
import ru.progwards.tasktracker.dto.TaskPriorityDtoPreview;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.TaskPriority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллеры TaskPriority
 * @author Pavel Khovaylo
 */
@RestController
@RequestMapping("/rest/task-priority/")
public class TaskPriorityController {
    /**
     * конвертер TaskPriority <-> TaskPriorityDtoFull
     */
    @Autowired
    private Converter<TaskPriority, TaskPriorityDtoFull> converterFull;
    /**
     * конвертер TaskPriority <-> TaskPriorityDtoPreview
     */
    @Autowired
    private Converter<TaskPriority, TaskPriorityDtoPreview> converterPreview;
    /**
     * сервисный класс для получение списка TaskPriority
     */
    @Autowired
    private GetService<Long, TaskPriority> taskPriorityGetService;
    /**
     * сервисный класс для получение TaskPriority
     */
    @Autowired
    private GetListService<TaskPriority> taskPriorityGetListService;
    /**
     * сервисный класс для создания TaskPriority
     */
    @Autowired
    private CreateService<TaskPriority> taskPriorityCreateService;
    /**
     * сервисный класс для обновления TaskPriority
     */
    @Autowired
    private RefreshService<TaskPriority> taskPriorityRefreshService;
    /**
     * сервисный класс для удаления TaskPriority
     */
    @Autowired
    private RemoveService<TaskPriority> taskPriorityRemoveService;

    /**
     * по запросу получаем список TaskPriorityDtoPreview
     * @return список TaskPriorityDto
     */
    @GetMapping("list")
    public ResponseEntity<Collection<TaskPriorityDtoPreview>> get() {
        Collection<TaskPriorityDtoPreview> taskPriorityDtoPreviews =
                taskPriorityGetListService.getList().stream().
                        map(e -> converterPreview.toDto(e)).collect(Collectors.toList());

        return new ResponseEntity<>(taskPriorityDtoPreviews, HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный TaskPriorityDtoFull; если такового нет, то бросаем исключение NotFoundException
     * @param id идентификатор TaskPriorityDtoFull
     * @return TaskPriorityDto
     */
    @GetMapping("{id}")
    public ResponseEntity<TaskPriorityDtoFull> get(@PathVariable("id") Long id) {
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
    @PostMapping("create")
    public ResponseEntity<TaskPriorityDtoFull> create(@RequestBody TaskPriorityDtoFull taskPriorityDtoFull) {
        if (taskPriorityDtoFull == null)
            throw new BadRequestException("Project is null");

        taskPriorityCreateService.create(converterFull.toModel(taskPriorityDtoFull));

        return new ResponseEntity<>(taskPriorityDtoFull, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий TaskPriorityDtoFull
     * @param id идентификатор изменяемого TaskPriorityDtoFull
     * @param taskPriorityDtoFull измененный TaskPriorityDtoFull
     */
    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody TaskPriorityDtoFull taskPriorityDtoFull) {
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
    public void delete(@PathVariable("id") Long id) {
        TaskPriority taskPriority = taskPriorityGetService.get(id);
        if (taskPriority == null)
            throw new NotFoundException("Not found a taskPriority with id=" + id);

        taskPriorityRemoveService.remove(taskPriority);
    }
}
package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskPriorityDtoFull;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.controller.exception.NotFoundException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.TaskPriority;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллеры TaskPriority
 * @author Pavel Khovaylo
 */
@RestController
@RequestMapping("/rest/task-priority/")
public class TaskPriorityController {

    @Autowired
    private Converter<TaskPriority, TaskPriorityDtoFull> converter;

    @Autowired
    private GetService<Long, TaskPriority> taskPriorityGetService;

    @Autowired
    private GetListService<TaskPriority> taskPriorityGetListService;

    @Autowired
    private CreateService<TaskPriority> taskPriorityCreateService;

    @Autowired
    private RefreshService<TaskPriority> taskPriorityRefreshService;

    @Autowired
    private RemoveService<TaskPriority> taskPriorityRemoveService;

    /**
     * по запросу получаем список TaskPriorityDto
     * @return список TaskPriorityDto
     */
    @GetMapping("list")
    public ResponseEntity<Collection<TaskPriorityDtoFull>> get() {
        Collection<TaskPriorityDtoFull> taskPriorityDtoFulls =
                taskPriorityGetListService.getList().stream().
                        map(e -> converter.toDto(e)).collect(Collectors.toList());

        return new ResponseEntity<>(taskPriorityDtoFulls, HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный TaskPriorityDto; если такового нет, то бросаем исключение NotFoundException
     * @param id идентификатор TaskPriorityDto
     * @return TaskPriorityDto
     */
    @GetMapping("{id}")
    public ResponseEntity<TaskPriorityDtoFull> get(@PathVariable("id") Long id) {
        TaskPriority taskPriority = taskPriorityGetService.get(id);
        if (taskPriority == null)
            throw new NotFoundException("Not found a taskPriority with id=" + id);

        return new ResponseEntity<>(converter.toDto(taskPriority), HttpStatus.OK);
    }

    /**
     * по запросу создаём TaskPriorityDto
     * @param taskPriorityDtoFull передаем наполненный TaskPriorityDto
     * @return созданный TaskPriorityDto
     */
    @PostMapping("create")
    public ResponseEntity<TaskPriorityDtoFull> create(@RequestBody TaskPriorityDtoFull taskPriorityDtoFull) {
        if (taskPriorityDtoFull == null)
            throw new BadRequestException("Project is null");

        taskPriorityCreateService.create(converter.toModel(taskPriorityDtoFull));

        return new ResponseEntity<>(taskPriorityDtoFull, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий TaskPriorityDto
     * @param id идентификатор изменяемого TaskPriorityDto
     * @param taskPriorityDtoFull измененный TaskPriorityDto
     */
    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody TaskPriorityDtoFull taskPriorityDtoFull) {
        if (id == null)
            throw new BadRequestException("Id is null");

        if (taskPriorityDtoFull == null)
            throw new BadRequestException("Project is null");

        taskPriorityDtoFull.setId(id);
        taskPriorityRefreshService.refresh(converter.toModel(taskPriorityDtoFull));
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
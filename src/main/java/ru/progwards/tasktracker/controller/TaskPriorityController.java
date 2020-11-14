package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.TaskPriorityDto;
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
@RequestMapping("/rest/taskpriority/")
public class TaskPriorityController {

    @Autowired
    private Converter<TaskPriority, TaskPriorityDto> converter;

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
    public ResponseEntity<Collection<TaskPriorityDto>> get() {
        Collection<TaskPriorityDto> taskPriorityDtos =
                taskPriorityGetListService.getList().stream().
                        map(e -> converter.toDto(e)).collect(Collectors.toList());

        return new ResponseEntity<>(taskPriorityDtos, HttpStatus.OK);
    }

    /**
     * по запросу получаем нужный TaskPriorityDto; если такового нет, то бросаем исключение NotFoundException
     * @param id идентификатор TaskPriorityDto
     * @return TaskPriorityDto
     */
    @GetMapping("{id}")
    public ResponseEntity<TaskPriorityDto> get(@PathVariable("id") Long id) {
        TaskPriority taskPriority = taskPriorityGetService.get(id);
        if (taskPriority == null)
            throw new NotFoundException("Not found a taskPriority with id=" + id);

        return new ResponseEntity<>(converter.toDto(taskPriority), HttpStatus.OK);
    }

    /**
     * по запросу создаём TaskPriorityDto
     * @param taskPriorityDto передаем наполненный TaskPriorityDto
     * @return созданный TaskPriorityDto
     */
    @PostMapping("create")
    public ResponseEntity<TaskPriorityDto> create(@RequestBody TaskPriorityDto taskPriorityDto) {
        if (taskPriorityDto == null)
            throw new BadRequestException("Project is null");

        taskPriorityCreateService.create(converter.toModel(taskPriorityDto));

        return new ResponseEntity<>(taskPriorityDto, HttpStatus.OK);
    }

    /**
     * по запросу обновляем существующий TaskPriorityDto
     * @param id идентификатор изменяемого TaskPriorityDto
     * @param taskPriorityDto измененный TaskPriorityDto
     */
    @PostMapping("{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody TaskPriorityDto taskPriorityDto) {
        if (id == null)
            throw new BadRequestException("Id is null");

        if (taskPriorityDto == null)
            throw new BadRequestException("Project is null");

        taskPriorityDto.setId(id);
        taskPriorityRefreshService.refresh(converter.toModel(taskPriorityDto));
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
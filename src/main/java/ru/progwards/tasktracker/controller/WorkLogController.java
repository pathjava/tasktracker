package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.WorkLog;
import ru.progwards.tasktracker.service.CreateService;
import ru.progwards.tasktracker.service.GetService;
import ru.progwards.tasktracker.service.RefreshService;
import ru.progwards.tasktracker.service.RemoveService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с журналом работ (WorkLog)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkLogController {

    private final @NonNull GetService<Long, WorkLog> workLogGetService;
    private final @NonNull CreateService<WorkLog> workLogCreateService;
    private final @NonNull RemoveService<WorkLog> workLogRemoveService;
    private final @NonNull RefreshService<WorkLog> workLogRefreshService;
    private final @NonNull GetService<Long, Task> taskGetService;
    private final @NonNull Converter<WorkLog, WorkLogDtoFull> converter;

//    @Autowired
//    private GetListByTaskService<Long, WorkLog> listByTaskService;

    /**
     * Метод получения одной записи журнала работ (WorkLog)
     *
     * @param id идентификатор
     * @return возвращает WorkLogDtoFull
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<WorkLogDtoFull> get(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        WorkLogDtoFull workLogDto = converter.toDto(workLogGetService.get(id));

        return new ResponseEntity<>(workLogDto, HttpStatus.OK);
    }

    /**
     * Метод получения списка журнала работ (WorkLog) по идентификатору задачи (Task)
     *
     * @param id идентификатор задачи, для которой необходимо вывести логи
     * @return лист WorkLogDtoFull
     */
    @GetMapping(value = "/task/{id}/worklogs")
    public ResponseEntity<List<WorkLogDtoFull>> getList(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Task task = taskGetService.get(id);
        List<WorkLogDtoFull> list = task.getWorkLogs().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());

        /* old version */
//        Collection<WorkLogDtoFull> logs = listByTaskService.getListByTaskId(id).stream()
//                .map(workLog -> converter.toDto(workLog))
//                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список WorkLogDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод создания одной записи журнала работ (WorkLog)
     *
     * @param workLogDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный WorkLogDtoFull
     */
    @PostMapping(value = "/worklog/create")
    public ResponseEntity<WorkLogDtoFull> create(@RequestBody WorkLogDtoFull workLogDto) {
        if (workLogDto == null)
            throw new BadRequestException("WorkLogDtoFull == null");

        workLogCreateService.create(converter.toModel(workLogDto));

        return new ResponseEntity<>(workLogDto, HttpStatus.OK);

        /* old version */
//        WorkLog workLog = converter.toModel(workLogDto);
//        workLogCreateService.create(workLog);
//        WorkLogDtoFull createdWorkLog = converter.toDto(workLog);
//
//        return new ResponseEntity<>(createdWorkLog, HttpStatus.OK);
    }

    /**
     * Метод обновления одной записи журнала работ (WorkLog)
     *
     * @param workLogDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает WorkLogDtoFull
     */
    @PutMapping(value = "/worklog/{id}/update")
    public ResponseEntity<WorkLogDtoFull> update(@PathVariable Long id, @RequestBody WorkLogDtoFull workLogDto) {
        if (workLogDto == null)
            throw new BadRequestException("WorkLogDtoFull == null");

        if (!id.equals(workLogDto.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        workLogRefreshService.refresh(converter.toModel(workLogDto));

        return new ResponseEntity<>(workLogDto, HttpStatus.OK);

        /* old version */
//        WorkLog workLog = converter.toModel(workLogDto);
//        workLogRefreshService.refresh(workLog);
//        WorkLogDtoFull updatedWorkLog = converter.toDto(workLog);
//
//        return new ResponseEntity<>(updatedWorkLog, HttpStatus.OK);
    }

    /**
     * Метод удаления одной записи журнала работ (WorkLog)
     *
     * @param id идентификатор одной записи журнала работ (WorkLog)
     * @return статус ответа
     */
    @DeleteMapping(value = "/worklog/{id}/delete")
    public ResponseEntity<WorkLogDtoFull> delete(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        WorkLog workLog = workLogGetService.get(id);
        workLogRemoveService.remove(workLog);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

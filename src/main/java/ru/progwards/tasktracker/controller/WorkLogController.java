package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.dto.WorkLogDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.WorkLog;
import ru.progwards.tasktracker.service.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с журналом работ (WorkLog)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkLogController {

    private final @NonNull GetService<Long, WorkLog> workLogGetService;
    private final @NonNull GetListService<WorkLog> workLogGetListService;
    private final @NonNull CreateService<WorkLog> workLogCreateService;
    private final @NonNull RemoveService<WorkLog> workLogRemoveService;
    private final @NonNull RefreshService<WorkLog> workLogRefreshService;
    private final @NonNull GetService<Long, Task> taskGetService;
    private final @NonNull Converter<WorkLog, WorkLogDtoFull> workLogDtoFullConverter;
    private final @NonNull Converter<WorkLog, WorkLogDtoPreview> workLogDtoPreviewConverter;

    /**
     * Метод получения одной записи журнала работ (WorkLog)
     *
     * @param id идентификатор
     * @return возвращает WorkLogDtoFull
     */
    @GetMapping(value = "/worklog/{id}", produces = "application/json")
    public ResponseEntity<WorkLogDtoFull> get(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        WorkLogDtoFull workLogDto = workLogDtoFullConverter.toDto(workLogGetService.get(id));

        return new ResponseEntity<>(workLogDto, HttpStatus.OK);
    }

    /**
     * Метод получения списка журнала работ (WorkLog) по идентификатору задачи (Task)
     *
     * @param id идентификатор задачи, для которой необходимо вывести логи
     * @return лист WorkLogDtoFull
     */
    @GetMapping(value = "/task/{id}/worklogs", produces = "application/json")
    public ResponseEntity<List<WorkLogDtoFull>> getListByTask(@PathVariable Long id) {
        if (id == null)
            throw new BadRequestException("Id: " + id + " не задан или задан неверно!");

        Task task = taskGetService.get(id);
        List<WorkLogDtoFull> list = task.getWorkLogs().stream()
                .map(workLogDtoFullConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список WorkLogDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения списка всего журнала работ (WorkLog)
     *
     * @return лист WorkLogDtoFull
     */
    @GetMapping(value = "/worklog/list", produces = "application/json")
    public ResponseEntity<List<WorkLogDtoPreview>> getList() {
        List<WorkLogDtoPreview> list = workLogGetListService.getList().stream()
                .map(workLogDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

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
    @PostMapping(value = "/worklog/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<WorkLogDtoFull> create(@RequestBody WorkLogDtoFull workLogDto) {
        if (workLogDto == null)
            throw new BadRequestException("WorkLogDtoFull == null");

        WorkLog workLog = workLogDtoFullConverter.toModel(workLogDto);
        workLogCreateService.create(workLog);
        WorkLogDtoFull createdWorkLog = workLogDtoFullConverter.toDto(workLog);

        return new ResponseEntity<>(createdWorkLog, HttpStatus.OK);
    }

    /**
     * Метод обновления одной записи журнала работ (WorkLog)
     *
     * @param workLogDto сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает WorkLogDtoFull
     */
    @PutMapping(value = "/worklog/{id}/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<WorkLogDtoFull> update(@PathVariable Long id, @RequestBody WorkLogDtoFull workLogDto) {
        if (workLogDto == null)
            throw new BadRequestException("WorkLogDtoFull == null");

        if (!id.equals(workLogDto.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        WorkLog workLog = workLogDtoFullConverter.toModel(workLogDto);
        workLogRefreshService.refresh(workLog);
        WorkLogDtoFull updatedWorkLog = workLogDtoFullConverter.toDto(workLog);

        return new ResponseEntity<>(updatedWorkLog, HttpStatus.OK);
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

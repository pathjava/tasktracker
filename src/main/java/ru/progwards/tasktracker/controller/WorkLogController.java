package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.WorkLogDtoFull;
import ru.progwards.tasktracker.dto.WorkLogDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Task;
import ru.progwards.tasktracker.model.WorkLog;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.verificationstage.Create;
import ru.progwards.tasktracker.util.validator.verificationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с журналом работ (WorkLog)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class WorkLogController {

    private final GetService<Long, WorkLog> workLogGetService;
    private final GetListService<WorkLog> workLogGetListService;
    private final CreateService<WorkLog> workLogCreateService;
    private final RemoveService<WorkLog> workLogRemoveService;
    private final RefreshService<WorkLog> workLogRefreshService;
    private final GetService<Long, Task> taskGetService;
    private final Converter<WorkLog, WorkLogDtoFull> workLogDtoFullConverter;
    private final Converter<WorkLog, WorkLogDtoPreview> workLogDtoPreviewConverter;

    /**
     * Метод получения одной записи журнала работ (WorkLog)
     *
     * @param id идентификатор
     * @return возвращает WorkLogDtoFull
     */
    @GetMapping(value = "/worklog/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkLogDtoFull> get(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {

        WorkLogDtoFull workLogDto = workLogDtoFullConverter.toDto(workLogGetService.get(id));

        return new ResponseEntity<>(workLogDto, HttpStatus.OK);
    }

    /**
     * Метод получения списка журнала работ (WorkLog) по идентификатору задачи (Task)
     *
     * @param id идентификатор задачи, для которой необходимо вывести логи
     * @return лист WorkLogDtoFull
     */
    @GetMapping(value = "/task/{id}/worklogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkLogDtoFull>> getListByTask(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {

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
    @GetMapping(value = "/worklog/list", produces = MediaType.APPLICATION_JSON_VALUE)
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
     * @param dtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный WorkLogDtoFull
     */
    @PostMapping(value = "/worklog/create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkLogDtoFull> create(@Validated(Create.class) @RequestBody WorkLogDtoFull dtoFull) {

        WorkLog workLog = workLogDtoFullConverter.toModel(dtoFull);
        workLogCreateService.create(workLog);
        WorkLogDtoFull createdWorkLog = workLogDtoFullConverter.toDto(workLog);

        return new ResponseEntity<>(createdWorkLog, HttpStatus.OK);
    }

    /**
     * Метод обновления одной записи журнала работ (WorkLog)
     *
     * @param dtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает WorkLogDtoFull
     */
    @PutMapping(value = "/worklog/{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkLogDtoFull> update(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id,
                                                 @Validated(Update.class) @RequestBody WorkLogDtoFull dtoFull) {

        if (!id.equals(dtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        WorkLog workLog = workLogDtoFullConverter.toModel(dtoFull);
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
    public ResponseEntity<WorkLogDtoFull> delete(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {

        WorkLog workLog = workLogGetService.get(id);
        workLogRemoveService.remove(workLog);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

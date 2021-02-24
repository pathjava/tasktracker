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
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Positive;
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
    @GetMapping(value = "/workLog/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkLogDtoFull> get(@PathVariable @Positive Long id) {

        WorkLogDtoFull workLogDto = workLogDtoFullConverter.toDto(workLogGetService.get(id));

        return new ResponseEntity<>(workLogDto, HttpStatus.OK);
    }

    /**
     * Метод получения списка журнала работ (WorkLog) по идентификатору задачи (Task)
     *
     * @param id идентификатор задачи, для которой необходимо вывести логи
     * @return лист WorkLogDtoFull
     */
    @GetMapping(value = "/task/{id}/workLogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkLogDtoFull>> getListByTask(@PathVariable @Positive Long id) {

        Task task = taskGetService.get(id);
        List<WorkLogDtoFull> list = task.getWorkLogs().stream()
                .map(workLogDtoFullConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty())
            throw new NotFoundException("List WorkLogDtoFull is empty!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения списка всего журнала работ (WorkLog)
     *
     * @return лист WorkLogDtoFull
     */
    @GetMapping(value = "/workLog/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkLogDtoPreview>> getList() {
        List<WorkLogDtoPreview> list = workLogGetListService.getList().stream()
                .map(workLogDtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("List WorkLogDtoFull is empty!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод создания одной записи журнала работ (WorkLog)
     *
     * @param dtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный WorkLogDtoFull
     */
    @PostMapping(value = "/workLog/create",
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
    @PutMapping(value = "/workLog/{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkLogDtoFull> update(@PathVariable @Positive Long id,
                                                 @Validated(Update.class) @RequestBody WorkLogDtoFull dtoFull) {

        if (!id.equals(dtoFull.getId()))
            throw new BadRequestException("This operation is not possible!");

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
    @DeleteMapping(value = "/workLog/{id}/delete")
    public ResponseEntity<WorkLogDtoFull> delete(@PathVariable @Positive Long id) {

        WorkLog workLog = workLogGetService.get(id);
        workLogRemoveService.remove(workLog);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

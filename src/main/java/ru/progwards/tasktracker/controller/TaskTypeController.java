package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.TaskTypeDtoFull;
import ru.progwards.tasktracker.dto.TaskTypeDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.Project;
import ru.progwards.tasktracker.model.TaskType;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.verificationstage.Create;
import ru.progwards.tasktracker.util.validator.verificationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с типами задач (TaskType)
 *
 * @author Oleg Kiselev
 */
@RestController
@RequestMapping(value = "/rest/tasktype")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class TaskTypeController {

    private final CreateService<TaskType> taskTypeCreateService;
    private final GetService<Long, TaskType> taskTypeGetService;
    private final GetListService<TaskType> taskTypeGetListService;
    private final RemoveService<TaskType> taskTypeRemoveService;
    private final RefreshService<TaskType> taskTypeRefreshService;
    private final Converter<TaskType, TaskTypeDtoFull> dtoFullConverter;
    private final Converter<TaskType, TaskTypeDtoPreview> dtoPreviewConverter;
    private final GetService<Long, Project> projectGetService;

    /**
     * Метод создания типа задачи (TaskType)
     *
     * @param dtoFull сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает созданный TaskTypeDtoFull
     */
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskTypeDtoFull> create(@Validated(Create.class) @RequestBody TaskTypeDtoFull dtoFull) {

        TaskType taskType = dtoFullConverter.toModel(dtoFull);
        taskTypeCreateService.create(taskType);
        TaskTypeDtoFull createdTaskType = dtoFullConverter.toDto(taskType);

        return new ResponseEntity<>(createdTaskType, HttpStatus.OK);
    }

    /**
     * Метод получения типа задачи (TaskType)
     *
     * @param id идентификатор типа задачи
     * @return возвращает TaskTypeDtoFull
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskTypeDtoFull> get(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {

        TaskTypeDtoFull taskType = dtoFullConverter.toDto(taskTypeGetService.get(id));

        return new ResponseEntity<>(taskType, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции всех типов задач (TaskType)
     *
     * @return лист TaskTypeDtoFull
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskTypeDtoFull>> getList() {
        List<TaskTypeDtoFull> list = taskTypeGetListService.getList().stream()
                .map(dtoFullConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список TaskTypeDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод получения коллекции типов задач по идентификатору проекта (TaskType)
     *
     * @param id идентификатор проекта по которому необходимо получить все типы задач данного проекта
     * @return возвращает лист TaskTypeDtoFull
     */
    @GetMapping(value = "/{id}/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskTypeDtoPreview>> getListByProject(
            @PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {

        Project project = projectGetService.get(id);
        List<TaskTypeDtoPreview> list = project.getTaskTypes().stream()
                .map(dtoPreviewConverter::toDto)
                .collect(Collectors.toList());

        if (list.isEmpty())
            throw new NotFoundException("Список TaskTypeDtoPreview пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Метод обновления типа задачи (TaskType)
     *
     * @param id      идентификатор обновляемого типа задачи
     * @param dtoFull обновляемая сущность, приходящая в запросе из пользовательского интерфейса
     * @return возвращает обновленный TaskTypeDtoFull
     */
    @PutMapping(value = "/{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskTypeDtoFull> update(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id,
                                                  @Validated(Update.class) @RequestBody TaskTypeDtoFull dtoFull) {

        if (!id.equals(dtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        TaskType taskType = dtoFullConverter.toModel(dtoFull);
        taskTypeRefreshService.refresh(taskType);
        TaskTypeDtoFull updatedTaskType = dtoFullConverter.toDto(taskType);

        return new ResponseEntity<>(updatedTaskType, HttpStatus.OK);
    }

    /**
     * Метод удаления типа задачи (TaskType)
     *
     * @param id идентификатор удаляемого типа задачи
     * @return возвращает статус ответа
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<TaskTypeDtoFull> delete(@PathVariable @Min(1) @Max(Long.MAX_VALUE) Long id) {

        TaskType taskType = taskTypeGetService.get(id);
        taskTypeRemoveService.remove(taskType);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.*;
import ru.progwards.tasktracker.dto.WorkFlowActionDtoFull;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.exception.NotFoundException;
import ru.progwards.tasktracker.model.*;
import ru.progwards.tasktracker.model.WorkFlowAction;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Обработка запросов API по работе с действиями workflow
 *
 * @author Aleksandr Sidelnikov
 */
@RestController
@RequestMapping(value = "/rest/workflowaction")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class WorkFlowActionController {

    private final CreateService<WorkFlowAction> workFlowActionCreateService;
    private final GetService<Long, WorkFlowAction> workFlowActionGetService;
    private final GetListService<WorkFlowAction> workFlowActionGetListService;
    private final RemoveService<WorkFlowAction> workFlowActionRemoveService;
    private final RefreshService<WorkFlowAction> workFlowActionRefreshService;
    private final Converter<WorkFlowAction, WorkFlowActionDtoFull> dtoFullConverter;
    private final Converter<WorkFlowAction, WorkFlowActionDtoPreview> dtoPreviewConverter;

    /**
     * Получить список всех действий workflow
     * GET /rest/workflowaction/list
     *
     * @return список вложений
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkFlowActionDtoPreview>> getList() {
        List<WorkFlowActionDtoPreview> list = new ArrayList<>();
        for (WorkFlowAction workFlowAction : workFlowActionGetListService.getList()) {
            WorkFlowActionDtoPreview workFlowActionDtoPreview = dtoPreviewConverter.toDto(workFlowAction);
            list.add(workFlowActionDtoPreview);
        }

        if (list.isEmpty()) //TODO - пустая коллекция или нет возможно будет проверятся на фронте?
            throw new NotFoundException("Список WorkFlowActionDtoFull пустой!");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Получить конкретное действие workflow
     * GET /rest/workflowaction/{id}
     *
     * @param id идентификатор объекта
     * @return объект dto
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkFlowActionDtoFull> get(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {

        WorkFlowActionDtoFull workFlowAction = dtoFullConverter.toDto(workFlowActionGetService.get(id));

        return new ResponseEntity<>(workFlowAction, HttpStatus.OK);
    }

    /**
     * Создаём новое действие workflow
     * POST /rest/workflowaction/create
     *
     * @return объект после бизнес-логики
     */
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkFlowActionDtoFull> create(@Validated(Create.class) @RequestBody WorkFlowActionDtoFull dtoFull) {

        WorkFlowAction workFlowAction = dtoFullConverter.toModel(dtoFull);
        workFlowActionCreateService.create(workFlowAction);
        WorkFlowActionDtoFull createdWorkFlowAction = dtoFullConverter.toDto(workFlowAction);

        return new ResponseEntity<>(createdWorkFlowAction, HttpStatus.OK);
    }

    /**
     * Обновляем действие workflow
     * POST /rest/workflowaction/{id}/update
     *
     * @param id идентификатор изменяемого объекта
     */
    @PutMapping(value = "/{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkFlowActionDtoFull> update(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id,
                                                  @Validated(Update.class) @RequestBody WorkFlowActionDtoFull dtoFull) {

        if (!id.equals(dtoFull.getId()))
            throw new BadRequestException("Данная операция недопустима!");

        WorkFlowAction workFlowAction = dtoFullConverter.toModel(dtoFull);
        workFlowActionRefreshService.refresh(workFlowAction);
        WorkFlowActionDtoFull updatedWorkFlowAction = dtoFullConverter.toDto(workFlowAction);

        return new ResponseEntity<>(updatedWorkFlowAction, HttpStatus.OK);
    }

    /**
     * Удалить существующее действие workflow
     * POST /rest/workflowaction/{id}/delete
     *
     * @param id идентификатор удаляемого объекта
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<WorkFlowActionDtoFull> delete(@PathVariable @Min(0) @Max(Long.MAX_VALUE) Long id) {

        WorkFlowAction workFlowAction = workFlowActionGetService.get(id);
        workFlowActionRemoveService.remove(workFlowAction);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

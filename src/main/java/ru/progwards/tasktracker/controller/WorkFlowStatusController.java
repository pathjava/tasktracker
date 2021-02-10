package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoFull;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.WorkFlowStatus;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Обработка запросов API по работе со статусами workflow
 *
 * @author Gregory Lobkov
 */
@RestController
@RequestMapping("/rest/workflowstatus")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class WorkFlowStatusController {

    private final GetService<Long, WorkFlowStatus> getService;
    private final CreateService<WorkFlowStatus> createService;
    private final RemoveService<WorkFlowStatus> removeService;
    private final RefreshService<WorkFlowStatus> refreshService;
    private final GetListService<WorkFlowStatus> getListService;
    private final Converter<WorkFlowStatus, WorkFlowStatusDtoPreview> dtoPreviewConverter;
    private final Converter<WorkFlowStatus, WorkFlowStatusDtoFull> dtoConverter;


    /**
     * Получить список всех статусов workflow
     * GET /rest/workflowstatus/list
     *
     * @return список статусов workflow
     */
    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<WorkFlowStatusDtoPreview>> getList(
    ) {
        // получили список бизнес-объектов
        List<WorkFlowStatus> list = getListService.getList();
        List<WorkFlowStatusDtoPreview> resultList = new ArrayList<>(list.size());
        // преобразуем к dto
        for (WorkFlowStatus entity:list) {
            WorkFlowStatusDtoPreview dto = dtoPreviewConverter.toDto(entity);
            resultList.add(dto);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    /**
     * Получить конкретный статус workflow
     * GET /rest/workflowstatus/{id}
     *
     * @param id идентификатор объекта
     * @return объект dto
     */
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkFlowStatusDtoFull> get(
            @PathVariable("id") @Positive Long id
    ) {
        WorkFlowStatus vo = getService.get(id);
        WorkFlowStatusDtoFull entity = dtoConverter.toDto(vo);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


    /**
     * Создаём новый статус workflow
     * POST /rest/workflowstatus/create
     *
     * @param entity объект для создания
     * @return объект после бизнес-логики
     */
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkFlowStatusDtoFull> create(
            @RequestBody @Validated(Create.class) WorkFlowStatusDtoFull entity
    ) {
        WorkFlowStatus vo = dtoConverter.toModel(entity);
        createService.create(vo);
        WorkFlowStatusDtoFull result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Обновляем статус workflow
     * POST /rest/workflowstatus/{id}/update
     *
     * @param id идентификатор изменяемого объекта
     * @param entity измененный объект
     */
    @PostMapping(value = "/{id}/update")
    public ResponseEntity<WorkFlowStatusDtoFull> update(
            @PathVariable("id") @Positive Long id,
            @RequestBody @Validated(Update.class) WorkFlowStatusDtoFull entity
    ) {
        entity.setId(id);
        WorkFlowStatus vo = dtoConverter.toModel(entity);
        refreshService.refresh(vo);
        WorkFlowStatusDtoFull result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Удалить существующий статус workflow
     * POST /rest/workflowstatus/{id}/delete
     *
     * @param id идентификатор удаляемого объекта
     */
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable("id") @Positive Long id
    ) {
        WorkFlowStatus vo = getService.get(id);
        removeService.remove(vo);
    }

}
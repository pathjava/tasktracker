package ru.progwards.tasktracker.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.WorkFlowDtoFull;
import ru.progwards.tasktracker.dto.WorkFlowDtoPreview;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.model.WorkFlow;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.util.validator.validationstage.Create;
import ru.progwards.tasktracker.util.validator.validationstage.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Обработка запросов API по работе с workflow
 *
 * @author Gregory Lobkov
 */
@RestController
@RequestMapping("/rest/workflow")
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @NonNull})
@Validated
public class WorkFlowController {

    private final GetService<Long, WorkFlow> getService;
    private final CreateService<WorkFlow> createService;
    private final RemoveService<WorkFlow> removeService;
    private final RefreshService<WorkFlow> refreshService;
    private final GetListService<WorkFlow> getListService;
    private final Converter<WorkFlow, WorkFlowDtoPreview> dtoPreiewConverter;
    private final Converter<WorkFlow, WorkFlowDtoFull> dtoConverter;


    /**
     * Получить список всех workflow
     * GET /rest/workflow/list
     *
     * @return список workflow
     */
    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<WorkFlowDtoPreview>> getList(
    ) {
        // получили список бизнес-объектов
        Collection<WorkFlow> list = getListService.getList();
        List<WorkFlowDtoPreview> resultList = new ArrayList<>(list.size());
        // преобразуем к dto
        for (WorkFlow entity : list) {
            WorkFlowDtoPreview dto = dtoPreiewConverter.toDto(entity);
            resultList.add(dto);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    /**
     * Получить конкретный workflow
     * GET /rest/workflow/{id}
     *
     * @param id идентификатор объекта
     * @return объект dto
     */
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkFlowDtoFull> get(
            @PathVariable("id") @Min(0) Long id
    ) {
        WorkFlow vo = getService.get(id);
        WorkFlowDtoFull entity = dtoConverter.toDto(vo);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


    /**
     * Создаём новый workflow
     * POST /rest/workflow/create
     *
     * @param entity объект для создания
     * @return объект после бизнес-логики
     */
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkFlowDtoFull> create(
            @RequestBody @Validated(Create.class) WorkFlowDtoFull entity
    ) {
        WorkFlow vo = dtoConverter.toModel(entity);
        createService.create(vo);
        WorkFlowDtoFull result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Обновляем workflow
     * POST /rest/workflow/{id}/update
     *
     * @param id     идентификатор изменяемого объекта
     * @param entity измененный объект
     */
    @PostMapping(value = "/{id}/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkFlowDtoFull> update(
            @PathVariable("id") @Min(0) Long id,
            @RequestBody @Validated(Update.class) WorkFlowDtoFull entity
    ) {
        entity.setId(id);
        WorkFlow vo = dtoConverter.toModel(entity);
        refreshService.refresh(vo);
        WorkFlowDtoFull result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Удалить существующий workflow
     * POST /rest/workflow/{id}/delete
     *
     * @param id идентификатор удаляемого объекта
     */
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(
            @PathVariable("id") @Min(0) Long id
    ) {
        WorkFlow vo = getService.get(id);
        removeService.remove(vo);
    }

}
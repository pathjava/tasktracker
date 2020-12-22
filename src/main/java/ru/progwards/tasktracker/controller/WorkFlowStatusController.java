package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.dto.converter.Converter;
import ru.progwards.tasktracker.dto.WorkFlowStatusDtoFull;
import ru.progwards.tasktracker.exception.BadRequestException;
import ru.progwards.tasktracker.service.*;
import ru.progwards.tasktracker.model.WorkFlowStatus;

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
public class WorkFlowStatusController {

    @Autowired
    GetService<Long, WorkFlowStatus> getService;
    @Autowired
    CreateService<WorkFlowStatus> createService;
    @Autowired
    RemoveService<WorkFlowStatus> removeService;
    @Autowired
    RefreshService<WorkFlowStatus> refreshService;
    @Autowired
    GetListService<WorkFlowStatus> getListService;
    @Autowired
    Converter<WorkFlowStatus, WorkFlowStatusDtoFull> dtoConverter;


    /**
     * Получить список всех статусов workflow
     * GET /rest/workflowstatus/list
     *
     * @return список вложений
     */
    @GetMapping("/list")
    public ResponseEntity<Collection<WorkFlowStatusDtoFull>> getList() {
        // получили список бизнес-объектов
        Collection<WorkFlowStatus> list = getListService.getList();
        List<WorkFlowStatusDtoFull> resultList = new ArrayList<>(list.size());
        // преобразуем к dto
        for (WorkFlowStatus entity:list) {
            WorkFlowStatusDtoFull dto = dtoConverter.toDto(entity);
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
    @GetMapping("/{id}")
    public ResponseEntity<WorkFlowStatusDtoFull> get(@PathVariable("id") Long id) {
        if (id == null)
            throw new BadRequestException("Id is not set");

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
    @PostMapping("/create")
    public ResponseEntity<WorkFlowStatusDtoFull> create(@RequestBody WorkFlowStatusDtoFull entity) {
        if (entity == null)
            throw new BadRequestException("WorkFlowStatus is null");

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
    @PostMapping("/{id}/update")
    public ResponseEntity<WorkFlowStatusDtoFull> update(@PathVariable("id") Long id,
                                                        @RequestBody WorkFlowStatusDtoFull entity) {
        if (id == null)
            throw new BadRequestException("Id is not set");
        if (entity == null)
            throw new BadRequestException("WorkFlowStatus Id is null");

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
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        if (id == null)
            throw new BadRequestException("Workflow Id is not set");

        WorkFlowStatus vo = getService.get(id);
        removeService.remove(vo);
    }

}

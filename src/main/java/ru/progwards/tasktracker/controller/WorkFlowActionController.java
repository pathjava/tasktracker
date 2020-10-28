package ru.progwards.tasktracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.progwards.tasktracker.controller.converter.Converter;
import ru.progwards.tasktracker.controller.dto.WorkFlowActionDto;
import ru.progwards.tasktracker.controller.exception.BadRequestException;
import ru.progwards.tasktracker.service.facade.*;
import ru.progwards.tasktracker.service.vo.WorkFlowAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Обработка запросов API по работе с действиями workflow
 *
 * @author Gregory Lobkov
 */
@RestController
@RequestMapping("/rest/workflowaction")
public class WorkFlowActionController {

    @Autowired
    GetService<Long, WorkFlowAction> getService;
    @Autowired
    CreateService<WorkFlowAction> createService;
    @Autowired
    RemoveService<WorkFlowAction> removeService;
    @Autowired
    RefreshService<WorkFlowAction> refreshService;
    @Autowired
    GetListService<WorkFlowAction> getListService;
    @Autowired
    Converter<WorkFlowAction, WorkFlowActionDto> dtoConverter;


    /**
     * Получить список всех действий workflow
     * GET /rest/workflowaction/list
     *
     * @return список вложений
     */
    @GetMapping("/list")
    public ResponseEntity<Collection<WorkFlowActionDto>> getList() {
        // получили список бизнес-объектов
        Collection<WorkFlowAction> list = getListService.getList();
        List<WorkFlowActionDto> resultList = new ArrayList<>(list.size());
        // преобразуем к dto
        for (WorkFlowAction entity:list) {
            WorkFlowActionDto dto = dtoConverter.toDto(entity);
            resultList.add(dto);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


    /**
     * Получить конкретное действие workflow
     * GET /rest/workflowaction/{id}
     *
     * @param id идентификатор объекта
     * @return объект dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<WorkFlowActionDto> get(@PathVariable("id") Long id) {
        if (id == null)
            throw new BadRequestException("Id is not set");

        WorkFlowAction vo = getService.get(id);
        WorkFlowActionDto entity = dtoConverter.toDto(vo);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }


    /**
     * Создаём новое действие workflow
     * POST /rest/workflowaction/create
     *
     * @param entity объект для создания
     * @return объект после бизнес-логики
     */
    @PostMapping("/create")
    public ResponseEntity<WorkFlowActionDto> create(@RequestBody WorkFlowActionDto entity) {
        if (entity == null)
            throw new BadRequestException("WorkFlowAction is null");

        WorkFlowAction vo = dtoConverter.toModel(entity);
        createService.create(vo);
        WorkFlowActionDto result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Обновляем действие workflow
     * POST /rest/workflowaction/{id}/update
     *
     * @param id идентификатор изменяемого объекта
     * @param entity измененный объект
     */
    @PostMapping("/{id}/update")
    public ResponseEntity<WorkFlowActionDto> update(@PathVariable("id") Long id,
                                              @RequestBody WorkFlowActionDto entity) {
        if (id == null)
            throw new BadRequestException("Id is not set");
        if (entity == null)
            throw new BadRequestException("WorkFlowAction Id is null");

        entity.setId(id);
        WorkFlowAction vo = dtoConverter.toModel(entity);
        refreshService.refresh(vo);
        WorkFlowActionDto result = dtoConverter.toDto(vo);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Удалить существующее действие workflow
     * POST /rest/workflowaction/{id}/delete
     *
     * @param id идентификатор удаляемого объекта
     */
    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        if (id == null)
            throw new BadRequestException("Workflow Id is not set");

        WorkFlowAction vo = getService.get(id);
        removeService.remove(vo);
    }

}
